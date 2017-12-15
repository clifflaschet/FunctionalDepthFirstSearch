package com.functional.ai.search

import com.functional.ai.search.Vault.VaultCombination

import scala.annotation.tailrec
import scalaz.Scalaz._
import scalaz._

/**
  * Used for storing the state of the search.
  * @param frontier
  * @param currentNode
  */
case class SearchState(frontier: List[TreeNode], currentNode: Option[TreeNode])

/**
  * Supporting classes for indicating the results of the search.
  */
trait SearchResult
case class Solution(combination: VaultCombination) extends SearchResult
case object Continue extends SearchResult
case object Failure extends SearchResult


object DepthFirstSearch {

  /**
    * Recursive function for searching the tree, uses searchNode.
    * Note the tail recursion.
    * @param result
    * @return
    */
  @tailrec def search(result: (SearchState, SearchResult)): (SearchState, SearchResult) ={
    if(result._2 != Continue)
      result
    else
      search(searchNode.run(result._1))
  }

  /**
    * Searches the first node in the frontier for a solution.
    * @return
    */
  def searchNode: State[SearchState, SearchResult] = for{
    empty <- frontierEmpty
    _ <- popNextNode
    goalReached <- isSolution
    _ <- expandNode
    currentState <- get
  } yield {
    if(empty) Failure
    else if(goalReached) Solution(currentState.currentNode.get.combination)
    else Continue
  }

  /**
    * Checks whether the frontier is empty.
    * @return
    */
  def frontierEmpty: State[SearchState, Boolean] = State[SearchState, Boolean](s => (s, s.frontier.isEmpty))

  /**
    * Get the next node from the frontier stack, if possible, and remove it from the frontier.
    * @return
    */
  def popNextNode: State[SearchState, Option[TreeNode]] = for{
    _ <- modify((s: SearchState) => {
      SearchState(s.frontier.tail, s.frontier.headOption)
    })
    currentState <- get
  }yield currentState.currentNode

  /**
    * Checks whether the current node is the correct the vault combination.
    * @return
    */
  def isSolution: State[SearchState, Boolean] = for{
    s <- get
  }yield s.currentNode match{
    case Some(treeNode) => Vault.checkCombination(treeNode.combination)
    case None => false
  }

  /**
    * Generates all child nodes of the current node.
    * @return
    */
  def expandNode: State[SearchState, Unit] = for{
    r <- modify((s: SearchState) => {
      s.currentNode match{

        //If the current node is defined, generate all child nodes and add them to the frontier stack.
        case Some(node) => {
          val children = TreeNode.children(node)
          s.copy(children ::: s.frontier)
        }

        //Otherwise, do not modify the search state and simply continue.
        case None => s.copy()
      }
    })
  }yield r
}