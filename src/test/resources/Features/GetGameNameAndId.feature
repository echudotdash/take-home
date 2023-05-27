Feature: Get Game name and id successfully using /game endpoint

  @game
  Scenario: user doesn't enter name parameter (default)
    Given request is prepared for the default endpoint
    When call to the default endpoint is made
    Then the returned name for the game must be "Sudoku"
    And the id should have increased

