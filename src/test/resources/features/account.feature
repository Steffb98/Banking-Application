Feature: Account tests

  Scenario: Retrieve one account is status OK
    When I retrieve account with id "NL44INHO0579841500"
    Then I get http status 200

  Scenario: Retrieve wrong user is status BAD_REQUEST
    When I retrieve account with id "NL44INHO0111111111"
    Then I get http status 404

  Scenario: Creating an account
    When I post an account
    Then I get http status 200

  Scenario: Changing the activity from an account
    When I change the activity from an account with id "NL44INHO0579841500"
    Then I get http status 200

  Scenario: Changing the activity from an account that does not exist
    When I change the activity from an account with id "NL44INHO0111111111"
    Then I get http status 404

  Scenario: Retrieve accounts with userId is status OK
    When I retrieve account with userId 100001
    Then I get http status 200
    And I get a list of 2 accounts