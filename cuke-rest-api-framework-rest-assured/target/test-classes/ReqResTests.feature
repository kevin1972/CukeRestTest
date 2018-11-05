##############################################################################################
# Feature: ReqResTests
#
##############################################################################################
Feature: ReqResTests
  In order to support users
  As an admin
  I want to test user functionality

  Background: 
    Given the following setup information:
      | Name    | Value             |
      | baseUri | https://reqres.in |

  #---------------------------------------------------------------------------
  Scenario: GetListUsers
    #---------------------------------------------------------------------------
    Given the following setup information:
      | Name     | Value      |
      | method   | GET        |
      | resource | /api/users |
    And the following parameter information:
      | Parameter | Value |
      | page      |     2 |

    When I make the call to the resource

    Then the response code should be "200"
    And the values should match the following table:
      | Field       | JPAth         | ExpectedValue |
      | total_pages | $.total_pages |             4 |

  #---------------------------------------------------------------------------
  Scenario: GetSingleUser
    #---------------------------------------------------------------------------
    Given the following setup information:
      | Name     | Value        |
      | method   | GET          |
      | resource | /api/users/2 |

    When I make the call to the resource

    Then the response code should be "200"
    And the values should match the following table:
      | Field       | JPAth     | ExpectedValue |
      | total_pages | $.data.id |             2 |

  #---------------------------------------------------------------------------
  Scenario: CreateUser
    #---------------------------------------------------------------------------
    Given the following setup information:
      | Name     | Value      |
      | method   | POST       |
      | resource | /api/users |
    And the message data is "{'name': 'morpheus','job': 'leader'}"

    When I make the call to the resource

    Then the response code should be "201"
    And the values should match the following table:
      | Field | JPAth  | ExpectedValue |
      | name  | $.name | morpheus      |
      | job   | $.job  | leader        |

  #---------------------------------------------------------------------------
  Scenario: UpdateUser
    #---------------------------------------------------------------------------
    Given the following setup information:
      | Name     | Value        |
      | method   | PUT          |
      | resource | /api/users/2 |
    And the message data is "{'name': 'morpheus','job': 'zion resident'}"

    When I make the call to the resource

    Then the response code should be "200"
    And the values should match the following table:
      | Field | JPAth  | ExpectedValue |
      | name  | $.name | morpheus      |
      | job   | $.job  | zion resident |

  #---------------------------------------------------------------------------
  Scenario: DeleteUser
    #---------------------------------------------------------------------------
    Given the following setup information:
      | Name     | Value        |
      | method   | DELETE       |
      | resource | /api/users/2 |

    When I make the call to the resource

    Then the response code should be "204"
