Feature: Cucumber The Goose Game Kata

Scenario: Add Player Pippo
  Given I have nothing
  When add player Pippo
  Then I have player Pippo

Scenario: Add Players Pippo, Pluto
  Given I have player Pippo
  When add player Pluto
  Then I have players Pippo Pluto

Scenario: Duplicated Player
  Given I have player Pippo
  When add player Pippo
  Then Pippo already existing player

Scenario: Start
  Given If there are two participants Pippo and Pluto on space Start
  When move Pippo 2, 2
  Then Pippo rolls 2, 2. Pippo moves from Start to 4
  When move Pluto 2, 1
  Then Pluto rolls 2, 1. Pluto moves from Start to 3
  When move Pippo 2, 5
  Then Pippo rolls 2, 5. Pippo moves from 4 to 11

Scenario: Victory
  Given If there is one participant Pippo on space 60
  When move Pippo 1, 2
  Then Pippo rolls 1, 2. Pippo moves from 60 to 63. Pippo Wins!!"

Scenario: Winning with the exact dice shooting
  Given If there is one participant Pippo on space 60
  When move Pippo 3, 2
  Then Pippo rolls 3, 2. Pippo moves from 60 to 63. Pippo bounces! Pippo returns to 61

Scenario: Winning with the exact dice shooting
  Given If there is one participant Pippo on space 60
  When move Pippo 3, 2
  Then Pippo rolls 3, 2. Pippo moves from 60 to 63. Pippo bounces! Pippo returns to 61

Scenario: Get to "The Bridge"
  Given If there is one participant Pippo on space 4
  When move Pippo 1, 1
  Then Pippo rolls 1, 1. Pippo moves from 4 to The Bridge. Pippo jumps to 12, The Goose. Pippo moves again and goes to 16
   
Scenario: Prank
  Given If there are two participants Pippo and Pluto respectively on spaces 15 and 17
  When move Pippo 1, 1
  Then Pippo rolls 1, 1. Pippo moves from 15 to 17. On 17 there is Pluto, who returns to 15

