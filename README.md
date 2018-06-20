Header: Help Loop by Rubin Luitel and Jatin Alla, 1 May 2018. Updated 5/22/2018
Description:
   * Our program is a planner that allows the user to keep track of daily tasks for an more organized and stress free life. This is targeted towards high school students who sometimes feel lost and need to get their life back together. Our app helps manage this stress. Our application works by integrating schoolloop assignments into an agenda. This agenda is personalize so the user can store all the event into one agenda instead of referring to multiple sites to know what is assigned. Our program mainly targets high school students, specifically Homestead High School students who seek help with organization. One of the primary feature of our program is a chat, the user has a chat which consists of other users who are in a similar class as the user. Our second feature is an agenda, we map all the assignments from school loop into a simple agenda that the user can easily navigate around, the user also has an option to add items into the agenda manually. Another feature that we have is notification, the notification will remind user about the assignments that are due soon. Additionally, we also recommend a quick healthy activity for the user after they complete a task.
Instructions: 
* Create an account with your normal email
* Fill out school loop login info and click next to continue
* User is directed to the main page where they see all their assignments from schoolloop
* the user can click the “pencil mark” button on the bottom right corner to manually add an assignment
* User can click the “check mark” next to every assignment to remove them from the list
* The assigment page is the first tab, there is a second tab called “chats", click on the chat tab to go to the page that shows a list of chat rooms
* Click on each chatroom to join the chatroom
* Can navigate back with back button on the upper left corner
Features List:
* Must-have:
   * Login user though schoolloop
   * Get their assignments from schoolloop and display on the main page
   * Make a chat room for the user, the chat room consists of people with similar class
   * We remind the user about the assignments that is due(through notifications)
   * Allow the user to check mark an assignment to indicate whether the assignment is finished or not
   * Allow the user to manually enter an assignment
* Want-to-have:
   * The chatroom will be modified, the user will join chat rooms that consists of others in higher math/science class than the user
   * Remind the user frequently if the assignment is due soon
   * Maybe instead of notification, an alarm?
   * Recommend a health activity to get rid of stress
* Stretch:
   * Frequent reminders for big assignments, such as tests/quiz/projects
   * Invite others to your current chat room
   * You can choose to be anonymous in the chat room
Class List:
* LoginActivity
   * Handles the login of the user
* SchoolloopDataRetrieve
   * This class retrieves the data from schooloop in the background and parses it and stores it in firebase
* AssignmentFragment
   * This class displays the data retrieved from school loop, it also stores the schoolloop data in firebase
* Assignment
   * This class is a class that represents an assignment, it has basic getters and setters, we need this class because firebase can store an object when storing data
* ChatFragment
   * This class creates a chat for the user, it has a list of different chats based on the users assignments
* FragmentStatePagerAdapter
   * This class returns the two fragments we created and saves the state in memory(manages assignmentfragment and chatfragment)
* RegisterActivity
   * This is a display of the page where the users make their account
* SchoolLoopInfoActivity
   * This is the page taken to right after the sign-up page, it allows the user to enter their schoolloop info and save it in firebase
* Chat
   * this is a class that handles displaying information about a chat
* ChatInfo
   * this is a class that represents a chat message
* ChatName
   * this is a class that represents a chat name
* CreateAssignmentActivity
   * this is a class that allows the user to manually enter an assignment information and stores the data into firebase
* Main
   * this class displays the assignmentfragment and chatfragment in a viewpager, it also calls schooloopdataretrieve class to do the background task, and it also setsup the notification for 4pm everyday
* NotificationReceiver
   * this class responds to the timed notification and displays that notification
* AssigmentDetailsActivity
   * this class will show the detailed information about the assignment if the assigment has detailed infos
* RecommendedActivity
   * this class will recommend a random activity to the user after the user completes an assignment
Responsibility List:
* Rubin Luitel
   * Retrieve the assignments from firebase and show it to the user
   * Create a group chat for the user based on the users assignment
   * webscrape schoolloop and login the user to schoolloop and retrieve the data
   * display all the chat messages to the user based on what chat group they are in
   * Create the UI
   * display the details of an assignment if the assignment has details
* Jatin Alla
   * Make an activity that allows the user to manually enter assignment and store it in firebase
   * Notification feature(reminds the user everyday at 5pm)
   * Store users schoolloopinfo into firebase
   * Work on the registration page and register the user in firebase
   * recommened a random activity to the user after they finish an assignment
   * Work on the login page and store login info on firebase
* Other sources
   * Google firebase github page - we learned how to retrieve data from firebase directly into our recylerview with this github page(link: https://github.com/firebase/FirebaseUI-Android/blob/master/database/README.md)
   * Stackoverflow Notificaiton with broadcastreceiver - we learned to trigger notification with this page(link: https://stackoverflow.com/questions/16491631/create-notification-with-broadcastreceiver)
   * Github auto scroll to the bottom - we learned how to auto scroll to the bottom of the chat when a new message is recieved(link: https://github.com/firebase/FirebaseUI-Android/issues/886)
* Feedback was given by Rafeh Muzaffar and Richard Qin
Recomendations:
1. some of the uml classes are islands
2. some stretch features (notably the first one) don't seem different from some of the must/want to have features
3. there are some classes on the uml but not the readme Overall opinion about uniqueness: the notifications feel unique and practical
