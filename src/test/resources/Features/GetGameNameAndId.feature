Feature: Get Game name and id successfully using /game endpoint

  @default
  Scenario: doesn't enter name parameter (default)
    Given request is prepared for the default endpoint
    When call to the endpoint is made
    Then the returned name for the game must be "Sudoku"
    And the id should have increased

    @valid
  Scenario Outline: enters valid game name as parameter
    Given request is prepared for "<gameName>"
    When call to the endpoint is made
    Then the returned name for the game must be "<gameName>"
    And the id should have increased
      And the json response must match the json schema

  Examples:
  |gameName|
  |Chess   |
  |Football|

      @invalid    #-----BUG-------#
  Scenario Outline: enters invalid game name as parameter
    Given request is prepared for "<invalidGameName>"
    When call to the endpoint is made
    Then status code 400 should be received

    Examples:
    |invalidGameName|
    |$$$     |
    |109     |

