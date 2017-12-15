package com.functional.ai.search

import com.functional.ai.search.Vault.VaultCombination

/**
  * Represents a single tree node in the search tree.
  */
case class TreeNode(combination: VaultCombination)

object TreeNode {

  //The root node of the tree is an empty vault combination.
  val rootNode = TreeNode(Seq.empty)

  /**
    * Generates all child nodes of the given tree node.
    * @param node
    * @return
    */
  def children(node: TreeNode): List[TreeNode] ={
    //If child nodes can be generated (i.e. a number can be appended to the vault combination, this is based on the vault's number of cylinders).
    if(Vault.numberOfCylinders <= node.combination.length)
      List.empty
    else (Vault.vaultCombinationMin to Vault.vaultCombinationMax).toList.map{number =>
      TreeNode(node.combination :+ number)
    }
  }
}
