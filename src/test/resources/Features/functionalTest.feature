Feature: Functional Tests for the Application

@API
Scenario: Verifying ID and Text
Given User Prepare the without query parameter
When user send a GET call is made
Then the status code will be 200
And the response body contains id "id" and text "text"
And the user can verify custom the game "sudoku"
And the user verify the id is greater then zero