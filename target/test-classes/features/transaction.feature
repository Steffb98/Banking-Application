Feature: Transaction tests


  Scenario: Retrieve one transaction is status OK
    When I retrieve transaction with id 1
    Then I get http status 200

  Scenario: Retrieve wrong transaction is status BAD_REQUEST
    When I retrieve transaction with id 16
    Then I get http status 404

  Scenario: Retrieve transactions from user
    When I retrieve transaction from userId 100001
    Then I get http status 200

  Scenario: Retrieve transaction from account
    When I retrieve transaction from account "NL76INHO0103560337"
    Then I get http status 200

#  Scenario: Creating a transaction
#    When I post a transaction
#    Then I get http status 201