package com.functional.ai.search

/**
  * The vault that needs to be unlocked.
  */
object Vault {

  type VaultCombination = Seq[Int]

  val vaultCombinationMin = 0
  val vaultCombinationMax = 99
  val numberOfCylinders = 3

  val correctVaultCombination = Seq(72,2,42)

  def checkCombination: (VaultCombination) => Boolean = _.equals(correctVaultCombination)
}