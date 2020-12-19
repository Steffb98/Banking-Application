Feature: Users tests


  Scenario: Retrieve one user is status OK
    When I retrieve user with userId 100001
    Then I get http status 200

  Scenario: Retrieve wrong user is status BAD_REQUEST
    When I retrieve user with userId 5
    Then I get http status 404

  Scenario: Creating an user
    When I post an user
    Then I get http status 201

  Scenario: Changing the activity status of a user
    When I Change the status of an user with userId 100001
    Then I get http status 200

  Scenario: Changing the activity status of a user that does not exist
    When I Change the status of an user with userId 5
    Then I get http status 400


#  Scenario: Update user with userId
#    When I update user with userId 100002
#    Then I get http status 200