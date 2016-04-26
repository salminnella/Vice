# Vice
News Feed App For Vice<br>
This app is dedicated to downloading and displaying news articles from Vice.  Using the provided Vice API endpoints, this lets the user browse visualy rich news categories, as well as bookmark articles, adjust notification settings, and share articles via facebook.

This app consists of 2 activities, with the full navigation in the MainAcivity and article details for reading in the          ArticleActivity.  


MainActivity<br>
<img src="https://github.com/salminnella/Vice/blob/master/images/MainActivity.png" />
<br><br>
ArticleActivity<br>
<img src="https://github.com/salminnella/Vice/blob/master/images/ArticleActivity.png" />
<br><br>
<br><br>
<b>Bugs/Concerns:</b><br>
There are multiple layouts avaialble differentiating between phone and tablets.  The phone will have only one column in the category list, where as the tablet will have 2 columns in the list for portrait and 3 columns in the list for landscape.  

There is a memory issue for the tablet sizes when transitioning to landscape mode from portrait. Below is a screen shot of the error log (click for larger view).

<img src="https://raw.githubusercontent.com/salminnella/Vice/master/images/logcat.jpg" />

<ul>
<li>Progress bar was prioritized, but never implemented for slow network speeds.</li>
<li>Bookmarks tab needs a refresh on the list set. If the user enters a bookmarked article from the bookmarks tab, the list is not refreshed after a back button press.</li>
<li>Post Presentation deadline bug fix on duplicate bookmarks being added on notification preferences. As well as refactoring, and a bug in Notifications message updates</li>
<li>If a user enters an article, and then presses the back button before the glide library loads the image, the app will crash -- java.lang.IllegalArgumentException: You cannot start a load for a destroyed activity</li>
    <li>Progress bar was prioritized, but never implemented for slow network speeds.</li>
    <li>Bookmarks tab needs a refresh on the list set. If the user enters a bookmarked article from the bookmarks tab, the list is not refreshed after a back button press.</li>
    <li>Post Presentation deadline bug fix on duplicate bookmarks being added on notification preferences.</li>
    <li>If a user enters an article, and then presses the back button before the glide library loads the image, the app will crash -- java.lang.IllegalArgumentException: You cannot start a load for a destroyed activity</li>
</ul>
<br><br>
<br><br>
<b>Other Deliverables</b><br>

<b>WireFrame</b><br>
<img src="https://github.com/salminnella/Vice/blob/master/images/wireFrame.jpg" />
<br><br>
<b>Trello Board</b><br>
Containing user stories and features list<br>
https://trello.com/b/ejmJeQOO/vice-project
<br><br>
<b>Project Plan Proposal</b><br>
Includes user research, A project plan presentation  -- Competitive research in a Google Sheet<br>
https://docs.google.com/presentation/d/1mWkC9Xo9Tp7t5DK1MwwRBTYuFnxWX9_Cdy1-FHF-Bfw/edit#slide=id.p
<br><br>
<b>Feature Prioritization</b><br>
<img src="https://github.com/salminnella/Vice/blob/master/images/featurePriorities.jpg" /img>

