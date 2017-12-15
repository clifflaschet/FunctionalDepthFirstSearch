package com.functional.ai.search

import org.scalatest.FlatSpec
import org.scalatest.Matchers._

class DepthFirstSearchSpec extends FlatSpec {

  "A depth-first search" should
  "find the correct vault combination" in{

    import TreeNode._

    val result = DepthFirstSearch.search(SearchState(List(rootNode), None), Continue)

    result._2 shouldBe Solution(Vault.correctVaultCombination)
  }
}
