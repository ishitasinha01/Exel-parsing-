package com.example.musicgui;
// added jar files
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("Music database!");
        primaryStage.setScene(new Scene(root,800,600));
        primaryStage.show();
    }

    @Override
    public void init() throws Exception {
        super.init();
        if(!Datasource.getInstance().open()){
            System.out.println("fatal error ");
            Platform.exit();
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
        Datasouce.getInstance().close();
    }
}

//    Now in the right position, we'll place a v box containing several buttons.
//
//        Now pressing a button it's going to essentially perform a SQL operation.
//
//        So we're going to have three buttons, list artists,
//
//        a second button to show albums for a selected artist.
//
//        And thirdly, an update artist button.
//
//        Now that may not sound like much but each one here is going to demonstrate something different
//
//        that can be extended to similar operations.
//
//        So the list artist button will query all the data
//
//        in a table and present that to the user.
//
//        Now the show albums artist button
//
//        will need to do a query based on a selected record
//
//        and then display the results.
//
//        And finally, the update artist button will demonstrate what's involved
//
//        when performing one of the crud operations that changes existing data in a table.
//
//        So let's start out now by renaming the sample dot fxml file.
//
//        So I'm going to come over here right click that as we've done before refactor, rename
//
//        and we'll call that main dot to fxml.


////////////////////////////

//    So at the end of the last video, we got this error that we couldn't connect to database
//
//        and that was because we haven't got a suitable JDBC driver.
//
//        And if you recall, we actually set up
//
//        the JDBC driver in the previous application, but we haven't done it for this one.
//
//        So let's go ahead and actually do that now. So I'm going to come over here and click on the project,
//
//        right click, open module settings.
//
//We're going to go to libraries click on plus,
//
//        click on or select java and the actual
//
//        jar file is in the downloads folder SQLite JDBC
//
//        that's the drive where you've previously downloaded.
//
//        I'm going to add that to this library.
//
//        And you can see that it's going to be added to music UI, which is our current application our project.
//
//        Click on ok, click on ok again.
//
//        And that's the driver now added to the class path.
//
//        So we should now be able to run it again.
//
//        Now just before I do run it though,
//
//        note here that because we've copied the datasource.java file.
//
//        We've copied all the information including the path.
//
//        So the actual path at the moment is pointing to our previous project,
//
//        the database in that directory.
//
//        And if you recall we talked about if you're on windows selecting the path.
//
//        So you may want to update this path
//
//        or just know that you're using the database that's in that other project.
//
//        So I'm going to leave it on this default looking at the music folder,
//
//        which was again the previous application.
//
//        But just keep that in mind if you're wondering where the database is it's
//
//        actually grabbing that because we've copied the datasource.java file
//
//        it's using the same directory or the connection string pointing to that database.
//
//        All right. So let's go back to main. If we run it again now,
//
//        we should find that things will work okay.
//
//        Okay. This time the error has now disappeared because we've added the JDBC driver
//
//        for SQLite to our class path and you can see that the interface has appeared.
//
//        So when the application starts what we want to do is display all the artists
//
//        in the artist's table.
//
//        Now it's also possible that the user
//
//        might explicitly ask to see all the artists after they've performed some other query.
//
//        So consequently, we need to query all the artists when the application starts
//
//        and also potentially in response to user input.
//
//        Now remember that we always want to perform long tasks on a background thread
//
//        not on the main java fx application thread.
//
//        And if you recall from the thread section of this course
//
//        that when we want to run background threads from a java fx application,
//
//        we need to use helper classes
//
//        in the java fx dot concurrent package.
//
//        So I'm going to perform the following steps
//
//        not necessarily though in the order that I'm going to read them out.
//
//        Firstly, we want to create a task to perform the database action,
//
//        the query, the insert or whatever we're doing.
//
//        Secondly, we want to initialize the task with values required to perform the action
//
//        if that's necessary.
//
//        Third, we want to implement the task dot call method to perform the action.
//
//        Fourth, we want to bind the call results to the table views items property.
//
//        And then finally, we want to invoke the task.
//
//        Now because we may need to use this task from two places
//
//        at startup and when the user explicitly asks to see all artists,
//
//        we're not going to use an anonymous task class
//
//        that we create in response to user input
//
//        and you'll see an example of this later.
//
//        What we're going to do is we'll add a get all artists task class to the controller.
//
//        So let's go ahead and add that. So I'm just going to close this down.
//
//        So go back to our controller.
//
//        And what we want to do is add the method in there.
//
//        All right. So let's add this class, the get all artists task class
//
//        to the controller.
//
//        So I'm going to add the class below the public class controller code,
//
//        and we're going to call this one type in class
//
//        get all artists task
//
//        and that's extending task
//
//        which is from the java x fx dot concurrent.
//
//        Okay. Then we're going to overwrite the core method.
//
//        But we do want to change this around a little bit so we want to actually make this an observable list
//
//        that it's going to be returning.
//
//        So instead of object, it's going to be observable list
//
//        and it's going to be list of artist objects or artists.
//
//        And we don't need to add the exception there.
//
//        So I'm just going to remove that.
//
//        So instead of protected also we'll just make that public for simplicity.
//
//        So public it's returning an observable list of artist objects,
//
//        the core method is still the right one.
//
//        And in terms of what we're going to return here instead of returning null,
//
//        we're going to return fx collections
//
//        dot observable array list. I'll put that on the next line.
//
//        It's going to be data source
//
//        dot get instance, getting our singleton dot query artists.
//
//        And we're going to do data source
//
//        order by sending, so order underscore
//
//        by underscore ask is what we want to select there.
//
//        So that's what this method is ultimately going to call.
//
//        And again, with the reason that we're creating a separate class here and putting it in the controller
//
//        dot java file is we may need to use this task in two places.
//
//        Firstly, it's startup, but also when the user explicitly asks to see all artists.
//
//        So that's why we're not using an anonymous task class.
//
//        All right. So the class as you can see had to extend task.
//
//        And if we want to use data binding to populate the table,
//
//        the call method which we've reconfigured
//
//        has to return an observable list of artists
//
//        and you saw me use the correct java fx dot collections class
//
//        when importing. That's obviously very important,
//
//        and you can confirm that by checking the imports here on line three.
//
//        All right. So we've overwritten the core method to call the query artist method
//
//        in our datasource.java file. Now that method if you recall returns a list now.
//
//        now. We don't want to change it to return an observable list
//
//        because that would violate the separation between the model
//
//        and UI code.
//
//        Instead, the tasks creating an observable list from the list
//
//        that the query artist method returns.
//
//        So we're doing that by calling the fx collections dot observable array list
//
//        passing in the list that is returned from our data source,
//
//        which then ultimately will give us an observable list.
//
//        Now in a previous video, I said that we can use the model classes as is
//
//        and that would be true if we didn't want to take advantage of data binding,
//
//        but here we do actually want to take advantage of that.
//
//        So consequently,
//
//        we do need to make one small change to the artist class to achieve that.
//
//        So instead of storing the artist name in a string,
//
//        we need to store it as a simple string property.
//
//        Now this doesn't violate the model UI separation
//
//        because simple string property isn't a UI specific class.
//
//        Now we could have used it all along actually.
//
//        We'll do the same for the artist id though even though we're not displaying it.
//
//        Now because the fields will now be properties,
//
//        the getter and setter methods are going to change as well.
//
//        So let's go to the artist class and make those changes.
//
//        So at the moment, you can see that we've got them set up as private
//
//        as an int and a string for the id in their name and the artist class, but we're going to change that
//
//        to a simple integer property
//
//        and then a simple string property.
//
//        So the id and the name you can see that we've returned that. And consequently,
//
//        we also need to change the getters and setters. So for the id,
//
//        we're going to pass in a simple property
//
//        and a simple integer property and return the id.
//
//        Likewise, for the setting
//
//        we're going to get a simple integer property and likewise for the string as well.
//
//        We're going to return a simple string property
//
//        then when we're setting it we're going to return the simple string property.
//
//        All right. So that's our artist class updated.
//
//        And again, we're only doing this because we want to use data binding.
//
//        So since this is a simple application,
//
//        if we wanted to keep our model classes exactly as they were then we could do that
//
//        and we would explicitly then set the table items when the task completes.
//
//        Now there are two ways we could set the items.
//
//        Now I covered the first way in the threading section of the course.
//
//        We could call platform dot run later
//
//        when the query artist method returns its results.
//
//        And in the runnable, we pass to run later
//
//        we could set the table items artists
//
//        table dot get items dot set all artist results.
//
//        I also mentioned in the threading section that we can do more
//
//        with tasks than we covered, including running code when the call method completes.
//
//        Now to do that, we need to call task dot set on succeeded.
//
//        And it actually wants an event handler.
//
//        In our case so the most straightforward way to pass the code we want to execute
//
//        is to use a lambda.So we're going to do something like this.
//
//        We're going to do task dot set unsucceeded
//
//        and create our lambda maybe artist table
//
//        dot get items dot set all
//
//        artist results. That's ultimately what we're going to be doing,
//
//        making this a little bit easier to call when the core method completes.
//
//        All right.
//
//        So basically what I'm getting at here is
//
//        whenever data binding makes sense it's best to use it
//
//        because then we don't have to do anything when the task completes it's all handled automatically.
//
//        Now we have to do one more step for data binding to work though.
//
//        We have to map the name field in the artist class
//
//        to the name column in the table.
//
//        If you recall from the threading videos
//
//        that we accomplish this by adding a cell value factory
//
//        to the table column. So let's go ahead and do that in our fxml file.
//
//        So we'll look at adding a cell value factory for the artist name.
//
//        So we've got our table column here for our artist table name.
//
//        So to achieve this, we can actually just close off that ending
//
//        tag there
//
//        and under the table column we want to add a cell value factory. So it's cell value factory,
//
//        and then we want to add a property value factory.So property
//
//        value factory, property
//
//        equals and couldn't record name.
//
//        And then we need to close off our table column.
//
//        So basically, we've added a property value factory for the name
//
//        and that maps to the name field in the artist class.
//
//        So now that we've done that, we can add a method to the controller
//
//        that gets the artists. Let's go back to the controller.
//
//        I'm going to add some code in the controller class itself.
//
//        So firstly, we need an reference to the artist table. So that's going to be at fxml.
//
//        We want to private table view
//
//        which is a list of artists and we'll call the latest table.
//
//        And the actual method is going to be public void
//
//        list artists.
//
//        Now we need to create our tasks.
//
//        So it's going to be task. We want that observable list of artist objects.
//
//        Task is equal to new get all artist task.
//
//        It's calling the class that we've defined or at least instantiating it,
//
//        so we're creating a new task object.
//
//        And then we want to do artist table
//
//        dot items property dot bind
//
//        and task dot value property.
//
//        So you can see that we've created a new task object on line 17
//
//        then on line 18,
//
//        we're binding the result of the task, in this case,
//
//        the artist's observable list to the table views items property.
//
//        So they're actually bound to each other.
//
//        All right. So let's finish the video here. In the next video, we'll start
//
//        looking at how this is going to be invoked because the controller isn't created
//
//        until the fxml is loaded. So we have to figure out how we're going to get that to work.