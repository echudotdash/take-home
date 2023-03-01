Feature: Schema Verification

  @API1
  Scenario: Verifying the Schema using Query Parameter
    Given user prepare the request with Query key "name" and query parameter "Chess"
    When user send GET call with Query Parameter
    Then User should validate the JSON Schema