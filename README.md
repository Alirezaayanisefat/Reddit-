Reddit CLI Application
Welcome to the Reddit CLI application! This application provides basic functionalities similar to Reddit, allowing users to browse profiles, interact with subreddits, view timelines, read posts, and search for content.

How to Use
Main Menu Options:
1. View Profile: See your profile information and settings.
2. Subreddit Interaction: Interact with subreddits, such as joining or leaving.
3. View Timeline: Check your timeline to see posts from subreddits you've subscribed to.
4. View Posts: Read posts from various subreddits.
5. Search: Search for specific content, including users, subreddits, or posts.
   Running the Application
   Compile the Java source code.
   Run the compiled application file.
   Follow the prompts in the main menu to navigate through different functionalities.
   Code Overview
   The displayMainMenu function displays the main menu and returns the user's choice.
   The while loop ensures that the program continues running until the user decides to exit.
   Each case in the switch statement corresponds to a different functionality:
   Case 1: View Profile
   Case 2: Subreddit Interaction
   Case 3: View Timeline
   Case 4: View Posts
   Case 5: Search
   After executing each functionality, the program returns to the main menu for further interaction.
   Author
   [Alireza Ayanisefat]
### Post Class :
- in this class contains the methodes which user needs to make interaction with posts
- creating post and making it accessable in subreddits and timeline is done in this class to
### Subreddit Class:
- in this class subreddits are created and got assigned admins here
- also timeline proccess is done here too
- a search System for SubReddits is here throgh that user can interact with subreddits
- admins interaction with subreddit is done in this class
### AcountCreation Class:
- in this class acount creating proccess is done and login and log out too
- and main menu is in this class
- and am advanced search System is in this class which user can search for subreddits and usernames too
### Comment Class:

- user can give comments and see athor comments wich are owend by posts on subreddits and timeline
- over
### Attributes Class:

- this class contains the objects which user interacts with
- all the objects are created in this class
- it is somthing like a general class

### Main Class:

- this class only does the accessing proccess and conectes to Acount Class for other proccrss
