Meta:

Narrative:

Scenario: scenario description
Given User is on the [site]
When the site Title is [title]

Examples:
|site   |title     |
|Yahoo    |BBC - Home|

Scenario: 1. JavaScriptExecutor Examples
Given User is on the [site]
Then User should be able to get page title
When User refreshes the page

Examples:
|site		|
|Javascript		|

Scenario: SalesForce test
Given User is on the [site]
When User logins to the site
When User selects Customer option in drop down list

Examples:
|site   |
|Salesforce |

Scenario: SalesForce test
Given User is on the [site]
When User gets src for all mini images

Examples:
|site   |
|Etsy |

Scenario: Email Test
Given user wants to get resetPasswordLink from an email
Given user creates an email and add following [attachment] and sends it
Given user creates an email which has plain text and sends it
Given user creates a draft message email with plain text

Examples:
|site   |attachment|
|Etsy   |src/main/resources/uploadFile.jpg	|