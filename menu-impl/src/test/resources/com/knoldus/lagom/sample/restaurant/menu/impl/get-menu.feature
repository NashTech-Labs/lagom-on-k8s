Feature: Can I Get Menu?
  User wants to know whether he/she can Get Menu

  Scenario: User can Get Menu
    Given Restaurant has Menu
    When User asks for Menu
    Then User should Get Menu