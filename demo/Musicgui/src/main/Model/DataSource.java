package Model;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// created a new package and in that a new class for data base instead of main class
public class DataSource {
    public static final String DB_NAME = "music.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:C:\\Users\\Ishita Sinha\\Desktop\\java\\MusicDb\\" + DB_NAME;

    public static final String TABLE_ALBUMS = "albums";
    public static final String COLUMN_ALBUM_ID = "_id";
    public static final String COLUMN_ALBUM_NAME = "name";
    public static final String COLUMN_ALBUM_ARTIST = "artist";
    public static final int INDEX_ALBUM_ID = 1;
    public static final int INDEX_ALBUM_NAME = 2;
    public static final int INDEX_ALBUM_ARTIST = 3;


    public static final String TABLE_ARTISTS = "artists";
    public static final String COLUMN_ARTIST_ID = "i_d";
    public static final String COLUMN_ARTIST_NAME = "name";
    public static final int INDEX_ARTIST_ID = 1;
    public static final int INDEX_ARTIST_NAME = 2;

    public static final String TABLE_SONGS = "songs";
    public static final String COLUMN_SONG_TRACK = "track";
    public static final String COLUMN_SONG_TITLE = "title";
    public static final String COLUMN_SONG_ALBUM = "album";
    public static final int INDEX_SONG_ID = 1;
    public static final int INDEX_SONG_TRACK = 2;
    public static final int INDEX_SONG_TITLE = 3;
    public static final int INDEX_SONG_ALBUM = 4;

    public static final int ORDER_BY_NONE = 1;
    public static final int ORDER_BY_ASC = 2;
    public static final int ORDER_BY_DESC = 3; // in query artist instead of passing the select start from and then the table artists as were doing we build up a query string

    public static final String QUERY_ALBUMS_BY_ARTIST_START =
            "SELECT " + TABLE_ALBUMS + '.' + COLUMN_ALBUM_NAME + " FROM " + TABLE_ALBUMS +
                    " INNER JOIN " + TABLE_ARTISTS + " ON " + TABLE_ALBUMS + "." + COLUMN_ALBUM_ARTIST +
                    " = " + TABLE_ARTISTS + "." + COLUMN_ARTIST_ID +
                    " WHERE " + TABLE_ARTISTS + "." + COLUMN_ARTIST_NAME + " =\"";
    public static final String QUERY_ALBUMS_BY_ARTIST_SORT =
            " ORDER BY " + TABLE_ALBUMS + "." + COLUMN_ALBUM_NAME + " COLLATE NOCASE ";

    public static final String QUERY_ARTIST_FOR_SONG_START =
            "SELECT " + TABLE_ARTISTS + "." + COLUMN_ARTIST_NAME + ", " +
                    TABLE_ARTISTS + "." + COLUMN_ALBUM_NAME + ", " +
                    TABLE_SONGS + "." + COLUMN_SONG_TRACK + " FROM " + TABLE_SONGS +
                    " INNER JOIN " + "." + TABLE_ALBUMS + " ON " +
                    TABLE_SONGS + "." + COLUMN_SONG_ALBUM + " = " + TABLE_ALBUMS + "." + COLUMN_ALBUM_ID +
                    " INNER JOIN " + TABLE_ARTISTS + " ON " +
                    TABLE_ALBUMS + "." + COLUMN_ALBUM_ARTIST + " = " + TABLE_ARTISTS + "." + COLUMN_ARTIST_ID +
                    " WHERE " + TABLE_SONGS + "." + COLUMN_SONG_TITLE + " =\"";
    public static final String QUERY_ARTIST_FOR_SONG_SORT =
            " ORDER BY " + TABLE_ARTISTS + "." + COLUMN_ARTIST_NAME + ", " +
                    TABLE_ALBUMS + "." + COLUMN_ALBUM_NAME + " COLLATE NOCASE ";
    public static final String TABLE_ARTIST_SONG_VIEW = "artist_list";
    //CREATE VIEW OF NOT EXISTS artist_list AS SELECT artists.name, albums.name AS album,
    // songs.track, songs.title FROM songs INNER JOIN albums ON songs.album = album._id
    //INNER JOIN artists ON albums.artist = artists._id ORDER BY artists.name
    // albums.name, songs.track
    public static final String CREATE_ARTIST_FOR_SONG_VIEW = " CREATE VIEW IF NOT EXISTS " +
            TABLE_ARTIST_SONG_VIEW + " AS SELECT " + TABLE_ARTISTS + "." + COLUMN_ARTIST_NAME + ", " +
            TABLE_ALBUMS + "." + COLUMN_ALBUM_NAME + " AS " + COLUMN_SONG_ALBUM + ", " +
            TABLE_SONGS + "." + COLUMN_SONG_TRACK + ", " + TABLE_SONGS + "." + COLUMN_SONG_TITLE +
            " FROM " + TABLE_SONGS +
            " INNER JOIN " + TABLE_ALBUMS + " ON " + TABLE_SONGS +
            "." + COLUMN_SONG_ALBUM + " = " + TABLE_ALBUMS + "." + COLUMN_ALBUM_ID +
            " INNER JOIN " + TABLE_ARTISTS + " ON " + TABLE_ALBUMS + "." + COLUMN_ALBUM_ARTIST +
            " = " + TABLE_ARTISTS + "." + COLUMN_ARTIST_ID +
            " ORDER BY " +
            TABLE_ARTISTS + "." + COLUMN_ARTIST_NAME + ", " +
            TABLE_ALBUMS + "." + COLUMN_ALBUM_NAME + ", " +
            TABLE_SONGS + "." + COLUMN_SONG_TRACK;
    public static final String QUERY_VIEW_SONG_INFO = "SELECT " + COLUMN_ARTIST_NAME + ", " +
            COLUMN_SONG_ALBUM + ", " + COLUMN_SONG_TRACK + " FROM " + TABLE_ARTIST_SONG_VIEW +
            " WHERE " + COLUMN_SONG_TITLE + " =\"";

    public static final String QUERY_VIEW_SONG_INFO_PREP = "SELECT " + COLUMN_ARTIST_NAME + ", " +
            COLUMN_SONG_ALBUM + ", " + COLUMN_SONG_TRACK + " FROM " + TABLE_ARTIST_SONG_VIEW +
            " WHERE " + COLUMN_SONG_TITLE + " =?"; // PREPARED STATEMENT
    //SELECT name, album,track FROM artist_list WHERE title =?
    public static final String INSERT_ARTIST = "INSERT INTO " + TABLE_ARTISTS +
            '(' + COLUMN_ARTIST_NAME + ") VALUES(?)"; //TRANSACTIONS
    public static final String INSERT_ALBUMS = "INSERT INTO " + TABLE_ALBUMS +
            '(' + COLUMN_ALBUM_NAME + ", " + COLUMN_ALBUM_ARTIST + ") VALUES(?, ?)";
    public static final String INSERT_SONGS = "INSERT INTO " + TABLE_SONGS +
            '(' + COLUMN_ALBUM_NAME + ", " + COLUMN_SONG_TITLE + ", " + ", " + COLUMN_SONG_ALBUM +
            ") VALUES(?, ?, ?)";

    // we also need to declare an instance variable for the prepared statement and thats we only want to creatte it once we dont want to create every time a query because we only want it ti be pre compiled once
    // you might be tempted to have only one string for name and ine for id that we use for every table but its better to have one string constant per table that wau we can change the name of any column in a table
    // method to connect to data base insted in main we can connect form here in datasource class
    //its better to have one string contant every table that way we cam change the name of any column in a table without worrying about impacting on other tables
    public static final String QUERY_ARTIST = "SELECT " + COLUMN_ARTIST_ID + " FROM " +
            TABLE_ARTISTS + " WHERE " + COLUMN_ARTIST_NAME + " = ?";
    public static final String QUERY_ALBUM = "SELECT " + COLUMN_ALBUM_ID + " FROM " +
            TABLE_ALBUMS + " WHERE " + COLUMN_ALBUM_NAME + " = ?";

    private Connection conn;

    private PreparedStatement querySongInfoView;
    private PreparedStatement insertIntoArtists;
    private PreparedStatement insertIntoAlbums;
    private PreparedStatement insertIntoSongs;

    private PreparedStatement queryArtists;
    private PreparedStatement queryAlbum;

    // converting into singleton

    private static DataSource instance = new DataSource();
    private DataSource(){

    }
    private static DataSource getInstance(){

        return instance;

    }


    public boolean open() {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);
            querySongInfoView = conn.prepareStatement(QUERY_VIEW_SONG_INFO_PREP);
            insertIntoArtists = conn.prepareStatement(INSERT_ARTIST, Statement.RETURN_GENERATED_KEYS); // ONCE WE DO THAT WELL BE ABLE TO GET KEYS FROM THE PREPARED STATEMENT OBJECT
            insertIntoAlbums = conn.prepareStatement(INSERT_ALBUMS, Statement.RETURN_GENERATED_KEYS); // ONCE WE DO THAT WELL BE ABLE TO GET KEYS FROM THE PREPARED STATEMENT OBJECT
            insertIntoSongs = conn.prepareStatement(INSERT_SONGS); // ONCE WE DO THAT WELL BE ABLE TO GET KEYS FROM THE PREPARED STATEMENT OBJECT
            queryArtists = conn.prepareStatement(QUERY_ARTIST);
            queryAlbum = conn.prepareStatement(QUERY_ALBUM);

            return true;
        } catch (SQLException e) {
            System.out.println("couldn't connect to database" + e.getMessage());
            return false;
        }
    }

    public void close() {
        try {
            if (querySongInfoView != null) { // order is importgant we cant close the connection first because we need to open connection to close statements
                querySongInfoView.close();
            }// we close resources in reverse order in which they are opened
            if (insertIntoArtists != null) {
                insertIntoArtists.close(); // transaction
            }
            if (insertIntoAlbums != null) {
                insertIntoAlbums.close();
            }
            if (insertIntoSongs != null) {
                insertIntoSongs.close();
            }
            if (queryArtists != null) {
                queryArtists.close();
            }
            if (queryAlbum != null) {
                queryAlbum.close();
            }
            if (conn != null) {
                conn.close();
            }

        } catch (SQLException e) {
            System.out.println("couldn't close connection " + e.getMessage());
        }
    }

    public List<Artist> queryArtists(int sortOrder) {
        StringBuilder sb = new StringBuilder("SELECT * FROM ");
        sb.append(TABLE_ARTISTS);
        if (sortOrder != ORDER_BY_NONE) {
            sb.append(" ORDER BY ");
            sb.append(COLUMN_ARTIST_NAME);
            sb.append(" COLLATE NOCASE ");
            if (sortOrder == ORDER_BY_DESC) {
                sb.append("DESC");
            } else {
                sb.append("ASC");
            }
        }
//        Statement statement = null;
//        ResultSet results = null; remove this declaration after resources in try block
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(sb.toString()))// change after sb code ("SELECT * FROM " + TABLE_ARTISTS)) // return all the artis records with all column values

        {
//            statement=conn.createStatement(); // remove these after resources in tyr
//            results = statement.executeQuery("SELECT * FROM " + TABLE_ARTISTS); // return all the artis records with all column values
            List<Artist> artists = new ArrayList<>();
            while (results.next()) {
                Artist artist = new Artist(); //and for each record create new artist object
                artist.setId(results.getInt(INDEX_ARTIST_ID));// CHANGE IT AFTER INTRODUCING INDEX IN CODE(COLUMN_ARTIST_ID)); // we used result set getter methods to get the values from the record and set them to the artist instance and then we add the instance to the list once we are done looping we return the list to the caller
                artist.setName(results.getString(INDEX_ARTIST_NAME));//(COLUMN_ARTIST_NAME));
                artists.add(artist);
            }
            return artists;
        } catch (SQLException e) {
            System.out.println("Query failed " + e.getMessage());
            return null;
//        }finally { // we can get rid of entire finally clause after resourses in tyr
//            try{
//                if(results!=null){
//                    results.close();
//                }
//            }catch (SQLException e){
//                System.out.println("error closing resultset " + e.getMessage());
//            }
//            try{
//                if(statement !=null){
//                    statement.close();
//                }
//            }catch(SQLException e){
//                System.out.println("error closing statement "+ e.getMessage());
//            }// we got two catch method because either of those closes could actuallt catch or could throw the sql exception
//        }
        }
    }

    public List<String> queryAlbumsForArtist(String artistName, int sortOrder) {
        //SELECT albums.name FROM albums INNER JOIN artists ON albums.artist = artists._id WHERE artists.name = "Carole King" ORDER BY albums.name COLLATE NOCASE ASC
        StringBuilder sb = new StringBuilder(QUERY_ALBUMS_BY_ARTIST_START);//remove this after creating constants("SELECT ");
//        sb.append(TABLE_ALBUMS);
//        sb.append('.');
//        sb.append(COLUMN_ALBUM_NAME);
//        sb.append(" FROM ");
//        sb.append(TABLE_ALBUMS);
//        sb.append(" INNER JOIN ");
//        sb.append(TABLE_ARTISTS);
//        sb.append(" ON ");
//        sb.append(TABLE_ALBUMS);
//        sb.append('.');
//        sb.append(COLUMN_ALBUM_ARTIST);
//        sb.append(" = ");
//        sb.append(TABLE_ARTISTS);
//        sb.append('.');
//        sb.append(COLUMN_ARTIST_ID);
//        sb.append(" WHERE ");
//        sb.append(TABLE_ARTISTS);
//        sb.append('.');
//        sb.append(COLUMN_ARTIST_NAME);
//        sb.append(" =\"");
        sb.append(artistName);
        sb.append("\"");

        if (sortOrder != ORDER_BY_NONE) {
            sb.append(QUERY_ALBUMS_BY_ARTIST_SORT);
//            sb.append(" ORDER BY "); // CAN REMOVE AFTER ADDING CONTANTS TO CODE
//            sb.append(TABLE_ALBUMS);
//            sb.append('.');
//            sb.append(COLUMN_ALBUM_NAME);
//            sb.append(" COLLATE NOCASE ");
            if (sortOrder == ORDER_BY_DESC) {
                sb.append("DESC");
            } else {
                sb.append("ASC");

            }

        }
        System.out.println("SQL statement =" + sb.toString());
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(sb.toString())) {
            List<String> albums = new ArrayList<>();
            while (results.next()) {
                albums.add(results.getString(1));
            }
            return albums;
        } catch (SQLException e) {
            System.out.println("Query failed " + e.getMessage());
            return null;
        }


    }

    public List<SongArtist> queryArtistForSong(String songName, int sortOrder) {
        StringBuilder sb = new StringBuilder(QUERY_ARTIST_FOR_SONG_START);
        sb.append(songName);
        sb.append("\"");

        if (sortOrder != ORDER_BY_NONE) {
            sb.append(QUERY_ARTIST_FOR_SONG_SORT);
            if (sortOrder == ORDER_BY_DESC) {
                sb.append("DESC");
            } else {
                sb.append("ASC");
            }
        }
        System.out.println(" SQL Statement " + sb.toString());
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(sb.toString())) {
            List<SongArtist> songArtists = new ArrayList<>();
            while (results.next()) {
                SongArtist songArtist = new SongArtist();
                songArtist.setArtistName(results.getString(1));
                songArtist.setAlbumName(results.getString(2));
                songArtist.setTrack(results.getInt(3));
                songArtist.add(songArtist);
            }
            return songArtists;
        } catch (SQLException e) {
            System.out.println("query failed " + e.getMessage());
            return null;
        }
    }

    public void querySongsMetadata() {
        String sql = "SELECT * FROM " + TABLE_SONGS;
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery()) {
            ResultSetMetaData meta = results.getMetaData();
            int numColumns = meta.getColumnCount();
            for (int i = 1; i <= numColumns; i++) {
                System.out.format(" column %d in the songs table is name %s\n",
                        i.meta.getColumnName(i));
            }
        } catch (SQLException e) {
            System.out.println(" query failed " + e.getMessage());
        } //  using the result set metadata we can get information such as colum name and types and their attributes in other words now whether they are nullable etc
    }

    public int getCount(String table) {
        String sql = "SELECT COUNT(*),As count FROM " + table;
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(sql)) {

            int count = results.getInt("count");
            int min = results.getInt("min_id");
            System.out.format("Count = %d\n", count);
            return count;
        } catch (SQLException e) {
            System.out.println("query failed " + e.getMessage());
            return -1;
        }

    }

    public boolean createViewForSongArtists() {
        try (Statement statement = conn.createStatement()) {
            statement.execute(CREATE_ARTIST_FOR_SONG_VIEW));


            return true;
        } catch (SQLException e) {
            System.out.println(" crete view query failed " + e.getMessage());
            return false;
        }
    }

    public List<SongArtist> querySongInfoView(String title) {
//        StringBuilder sb = new StringBuilder(QUERY_VIEW_SONG_INFO);
//        sb.append(title);
//        sb.append("\""); // we dont need string builder after prepared statements
//        System.out.println(sb.toString());

        try //(Statement statement = conn.createStatement();
        // ResultSet results = statement.executeQuery(sb.toString()))  // NO USE OF THIS AFTER THE PREPARED STATEMENTS
        {
            querySongInfoView.setString(1, title);
            ResultSet results = querySongInfoView.executeQuery();
            List<SongArtist> songArtists = new ArrayList<>();
            while (results.next()) {
                SongArtist songArtist = new SongArtist();
                songArtist.setArtistName(results.getString(1));
                songArtist.setAlbumName(results.getString(2));
                songArtist.setTrack(results.getInt(3));
                songArtist.add(songArtist);
            }
            return songArtists;
        } catch (SQLException e) {
            System.out.println("Query failed " + e.getMessage());
            return null;
        }
    }

    // insert methods transactions

    private int insertArtist(String name) throws SQLException {
        queryArtists.setString(1, name);
        ResultSet results = queryArtist.executeQuery(); // queryint the artist table if the artist already exists if it does then we return the id that we retrieve from the results set and return the value beacuuse we dont no longer need to insert anything because we found artist on file
        if (results.next()) {
            return results.getInt(1);
        } else {
            // insert the artist cause not in file
            insertIntoArtists.setString(1, name);
            int affectedRows = insertIntoArtists.executeUpdate();

            if (affectedRows != 1) {
                throw new SQLException("Couldnt insert artist ");
            }

            ResultSet generatedKeys = insertIntoArtists.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("couldnt get _id for the artist"); // since we are inserting a single row we expect only one record to be returned if not then something wrong sql exception
            }
        }
    }

    private int insertAlbum(String name, int artistId) throws SQLException {
        queryAlbum.setString(1, name);
        ResultSet results = queryAlbum.executeQuery(); // queryint the artist table if the artist already exists if it does then we return the id that we retrieve from the results set and return the value beacuuse we dont no longer need to insert anything because we found artist on file
        if (results.next()) {
            return results.getInt(1);// if we give wrong col no this will show error java but the db will be updated
        } else {
            // insert the album cause not in file
            insertIntoAlbums.setString(1, name);
            insertIntoAlbums.setInt(2, artistId);
            int affectedRows = insertIntoAlbums.executeUpdate();

            if (affectedRows != 1) {
                throw new SQLException("Couldnt insert albums ");
            }

            ResultSet generatedKeys = insertIntoAlbums.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("couldnt get _id for the albums"); // since we are inserting a single row we expect only one record to be returned if not then something wrong sql exception
            }
        }
    }

    public void insertSongs(String title, String artist, String album, int track) {
        try {
            conn.setAutoCommit(false);
            int artistId = insertArtist(artist);
            int albumId = insertAlbum(album, artistId);
            insertIntoSongs.setInt(1, track);
            insertIntoSongs.setString(2, title);
            insertIntoSongs.setInt(3, albumId);
//            So we start off by inserting the artist
//
//            and then we insert the album using the artist id
//
//            that was returned from the insert artist method.
//
//            Once we get to that point, we can now insert the song.
//
//                    Now we do that by setting the values
//
//            and insert into a song's prepared statement, these three lines here.
//
//            And then we actually call the execute update to check that only one row was affected.
//            We've inserted the song and all its associated information.
//
//            So we want to commit the changes, and we're doing that on this line.
//
//            And once we've done that that also ends the transaction.
//
//            Now if something goes wrong and an exception is thrown,
//
//            we're calling this connection dot rollback down here.
//
//            And that's going to back out any changes we've made since starting the transaction.
//
//            And it will also end the transaction.
//
//                    And finally, down here in the finally block,
//
//                    we're actually setting the set order commit method
//
//            or calling it with the value of true
//
//            to return to the default auto commit behavior.
//
//                    Now because using transactions involves getting database locks,
//
//                    it's best practice to only turn off auto commit
//
//                for the duration of a transaction
//
//                and to turn it back on again immediately afterwards.
//
//                        Once it's turned back on unless we turn it off again,
//
//                any insert updates or deletes will be auto committed as soon as they've completed.
//
//                Now resetting the default behavior in the finally block ensures
//
//                that it will be done whether the transaction succeeds but also if it fails.
//

            int affectedRows = insertIntoSongs.executeUpdate();

            if (affectedRows == 1) {
                conn.commit();
//                    throw  new SQLException("Couldnt insert albums ");
            } else {
                throw new SQLException(" the song insert failed")
            }
//                ResultSet generatedKeys = insertIntoAlbums.getGeneratedKeys();
//                if(generatedKeys.next()){ // we dont need this code after above statements
//                    return generatedKeys.getInt(1); 
//                }else{
//                    throw new SQLException("couldnt get _id for the albums"); // since we are inserting a single row we expect only one record to be returned if not then something wrong sql exception
//                }
//            }
        } catch (SQLException e) {
            System.out.println("insert song exception " + e.getMessage());
            try {
                System.out.println(" performing rollback");
                conn.rollback();

            } catch (SQLException e2) {
                System.out.println(" bad things" + e.getMessage());
            }
        } finally {
            try {
                System.out.println("resetting default commit behavior");
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("couldnt reset auto commit " + e.getMessage());
            }
        }

    }
    // we can query a view just as we query a table
    // SELECT name, album, track FROM artist_list WHERE title ="title"
// when we are closing a prepared statement whichever result set is active will also be closed
}// we cant use placeholder for things like table and column names and thats because in order to precompile the statements the database needs to know which table were querying and which column we want
// we can use prepared statements with insert update and delete
//All right. So at this point, we're going to start taking a look at using JDBC
//
//        from within a UI application.
//
//        Now the JDBC calls themselves are the same.
//
//        But when do we create the data source class
//
//and how do classes in the application access it.
//
//        How do we perform database operations so that the user interface doesn't freeze?
//
//        And how do we report the results back to the user.
//
//        Well, the purpose of these videos is to answer those questions.
//
//        So note here that the goal isn't to create a pretty user interface.
//
//        The focus is on the questions that I mentioned previously
//
//        using a functional user interface.
//
//        So I'm going to assume at this point that you've watched the java fx section of the course
//
//        and therefore I'm only going to explain a user interface specific concept if it's new
//
//        and hasn't previously been and hasn't previously been
//
//        So therefore, I advise that if you haven't been through the java fx section of the course,
//
//        make sure you do check that out.
//
//        At least go through like a number of the initial videos
//
//        to get a bit of an idea on what
//
//        to expect and also what java fx is. And if you don't do that,
//
//        I think you may find that you possibly might get a little bit lost,
//
//        so I think it is important to do that.
//
//        So what I've done is I've created a new java fx project called music UI.
//
//        And the user interface itself we're going to be creating is fairly simple.
//
//        So we're going to be using a border pane with a table view in the center position.
//
//        Now we're also going to have a progress bar, which won't be visible when the application starts
//
//        for reasons that will become apparent. And at the bottom of the window
//
//        will be where that resides in a h box.
//
//        Now in the right position, we'll place a v box containing several buttons.
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
//
//        And now let's go ahead and add some fxml to it.
//
//        And just to save a bit of time, I'm just going to copy and paste the code here.
//
//        And I created this fxml using the scene builder.
//
//        So if you're looking at this coding
//
//        that I'm about to paste in and comparing that say to code that we've created
//
//        manually in the java fx section of the course,
//
//        it may be a bit wordier but that's because again it created a scene builder.
//
//        All right. So I'm just going to paste that code in. And this code is available in the resources section
//
//        of this video as well to save you having to type it all in.
//
//        So let's just go back now to our main.java file.
//
//        And what we want to do is make a few changes to the start method
//
//        and set the main windows title and size.
//
//        So for the title, we're going to change that from where it's currently
//
//        on hello world. We'll change it to music database.
//
//        And for the set scene, let's change the size there instead of 300 by
//
//        275. We'll change it to 800, but by let's make it 600
//
//        800 by 600.
//
//        All right. So let's just run this just to see what the user interface is looking like.
//
//        And there's our basic interface.
//
//        So you can see we've got an area here for contents. We've got our buttons to the right,
//
//        and we've also got this progress bar, now I did actually mention
//
//        that the progress bar is going to be invisible.
//
//        But I wanted to leave it by default the first time to show you what it looks like and where it's
//
//        positioned now that you've seen it down here down the bottom.
//
//        Let's close down the app and
//
//        make that invisible. So we'll go back to the main dot fxml.
//
//        And we'll change this progress bar over here on line 40.
//
//        And what we'll do is we'll put a new property here visible equals
//
//        false.
//
//        So now that we've done that if we go back to our main and run it again,
//
//        we should find that progress bar is now disappeared.
//
//        Okay. It's no progress bar,
//
//        and we'll actually make that visible where it's appropriate in this application that we're building. Okay.
//
//        And incidentally in a real-world application, we'll probably do it in such a way
//
//        that there was an empty space near the bottom,
//
//        but again we're not focusing on the user interface in this particular set of videos.
//
//        Now the other thing I want to point out here is we'll just go back to our main dot
//
//        fxml again and look at our table up here on line 16.
//
//        I want you to see how that word how we're setting the table column pref width
//
//        bar property over here along line 18.
//
//        What we're doing here is we're setting the width to the same width as the table view
//
//        so that the name column will occupy the entire table width.
//
//        Now we've used the notation there dollar
//
//        left curly brace artist table dot width and then right curly brace.
//
//        And what that means is that we want to set the pref width
//
//        to the width of the control with the fx id of artist table,
//
//        which in this case is our table view on line 16.
//
//        Now you can actually do that with any property.
//
//        All right. So now let's turn to using JDBC
//
//        what we're going to do first is copy the model classes we created
//
//        in the previous set of videos in this section of the course.
//
//        So I'm going to go and look at our
//
//        music projects model directory. So what I'm going to do is actually open up
//
//        the project I'm going to open to open recent.
//
//        And I'm going to go to music, which is our previous project.
//
//        I'm going to open that in a new window.
//
//        And again, that's the project that we were working on previously.
//
//        I'm just going to open the project pane over here so we can see that it is.
//
//        What I'm going to do then is navigate to the location
//
//        of the music properties model directory,
//
//        copy it and then paste it into our new project.
//
//        So what I'm going to do is come over here and right click on the package
//
//        for model, reveal in finder.
//
//        I think it'll be showing explorer and windows or something to those words to that effect.
//
//        But basically, I'm not going to actually just copy that entire folder, copy that.
//
//        I'm going to just close down this,
//
//        leaving our original project
//
//        and I'm going to just paste that into whatever the project
//
//        into whatever the package name is. In my case,
//
//        it's sample here I'm just going to click on sample, right click and paste.
//
//        Click on ok.
//
//        And you can see that, it's pasted in the not only the model directory,
//
//        but also all the source code that we were working on
//
//        in that pre in those previous videos.
//
//        So we've got our data source artist album,
//
//        song artist as well as song as well.
//
//        Now we don't actually need the song in song artist classes.
//
//        So for that reason, I'm going to just click on those and delete those.
//
//        So we've just gotten our album artist and data source.
//
//        Now we want the controller to be able to use the data source class.
//
//        Now in a real world application would probably have more than one controller
//
//        and that all need access.
//
//        So we could have each one create an instance of the class,
//
//        but which one then would be responsible for managing the connection
//
//        by calling the open and close methods.
//
//        Well, when working with a data source, it's actually quite common
//
//        to use a singleton pattern for the data source class.
//
//        Now it's called a singleton pattern and we have talked about this in the course previously
//
//        because we use it when we want an application to create only one instance of a particular class.
//
//        So every object that needs to call methods in the singleton
//
//        will use the same instance of the data source to do so.
//
//        To actually turn our data source class
//
//        into a singleton it's actually quite straightforward. So I'm going to double click it to open it.
//
//        And what we need to do is create a private constructor first.
//
//        Let's go ahead and do that. So I'm just going to go down past all these constant settings setups.
//
//        And we'll add one up here.
//
//        So I'm just going to do private data source.
//
//        Now since it's private, only the class will be able to create instances of itself.
//
//        In other words, no other class will be able to construct an instance of data source,
//
//        and that's what we want here.
//
//        So now that we've done that, we need to create the variable
//
//        that will hold that one instance of the class.
//
//        That every other class in the application will use
//
//        now this variable will need to be static. So we're going to
//
//        I'll put that above the private definition there.
//
//        So it's going to be private static data source,
//
//        and we'll call it instance, which is what it is.
//
//        All right. We now need to add the method
//
//        that every other class will use to access the instance.
//
//        Now we can create the singleton instance when we declare the variable
//
//        or we can create it in the static access method.
//
//        And that's where we're going to actually do it in this case.
//
//        We're going to call that get instance. So let's create
//
//        that I'll put that after the private constructor. So it's going to be public static
//
//        data source because we're going to be returning the data source
//
//        making sure by the way we're using the right data source here sample dot model.
//
//        And get instance is the name of the method.
//
//        Then the code is going to be if instance
//
//        is equal to null,
//
//        I'm going to put instance equals new data source.
//
//        And then below that, we're going to return instance.
//
//        So when an object wants to use the singleton instance, it calls get instance,
//
//        the public static method we've just created.
//
//        The method checks to see if the singleton instance has been previously created.
//
//        That's the code on line 126. If it has, it returns it.
//
//        But if it hasn't, then it creates the instance and then returns it. Now
//
//        this is called a lazy instantiation
//
//        because the instance isn't created until the first time it's needed.
//
//        Now this is perfectly valid code, but keep in mind it's not thread safe.
//
//        In other words, it's possible for a thread to be interrupted
//
//        after the check for null here.
//
//        Another thread could then run check for null and create the instance.
//
//        Then the first thread can run and create the second instance.
//
//        So our application would then have two instances on the go
//
//        and of course that defeats the purpose of using a singleton in the first place.
//
//        So consequently, instead what we should really do here
//
//        is we can create the instance when the instance variable is declared
//
//        and that will actually be thread safe.
//
//        So what I'm going to do is come up here to line 119
//
//        and change that to
//
//        leave the declaration private static data source instance
//
//        then we'll just put it equals new data source.
//
//        Now this way is also lazy instantiation because the instance won't be created
//
//        until the first time the class is loaded,
//
//        which will be the first time some other instance references the class
//
//        by calling the get instance method.
//
//        So now that we've actually done that, we can update the instance method,
//
//        which is going to be a lot simpler because we can remove this code here this if instance
//
//        equals null check and just return the instance only.
//
//        So whenever class now wants to call a method in the data source class,
//
//        it will do the following. It's going to be using basically
//
//        data source dot get instance
//
//        dot and whatever the method name is.
//
//        That's going to be the calling convention used
//
//        to access all the methods in this particular class.
//
//        All right. So I'll just remove that
//
//        we're just getting this error here about the
//
//        try with resources not supported at this language level.
//
//        So that's an error that we've seen before in the course we're going to fix it up by right clicking on the project name,
//
//        open module settings
//
//        project,
//
//        making sure that we're using lambdas and type annotation etc.
//
//        that fixes that error.
//
//        And because I deleted those two other classes,
//
//        we're actually getting some errors there. So I'm just going to remove those the
//
//        song artist. I'm going to remove that method altogether.
//
//        We've also got another one down here.
//
//        And these of course are all the methods that we've written previously,
//
//        which we're not going to be using those particular methods in this
//
//        part of the course. All right. So let's clear that up now so we haven't got any more errors.
//
//        So now that we've done that, we come back down here and look at our methods our open and closed methods.
//
//        We still have a question of where to call the open and close methods.
//
//        Now it would make sense to open a connection to the database
//
//        when an application is started
//
//        because our main window wants to show data from the database,
//
//        and it would also make sense to close the connection
//
//        when the user shuts down the application by closing the main window.
//
//        So what we're going to do here is make use of the java fx lifecycle methods
//
//        to open and close the database connection.
//
//        And if you recall, the application class
//
//        contains lifecycle methods that are either abstract or concrete,
//
//        but don't actually do anything.
//
//        So consequently, we're going to override the init method
//
//        to call the data source dot open method
//
//        and we'll override the stop method to call the data source dot close method.
//
//        Now the init method runs before the start method
//
//        that creates the user interface.
//
//        And the stop method runs when the application is shutting down
//
//        either because the application has explicitly done something to close it
//
//        or because the code called platform dot exit.
//
//        Both the init and stop methods are implemented in the application class,
//
//        but by default they don't do anything.
//
//        So let's go back to the main class.
//
//        And we'll actually get intellij
//
//        to create some methods for us in the main class.
//
//        So I'm just going to click in here right click and select generate.
//
//        And I'm going to click on select override methods. And the ones here that we want
//
//        are the init and stop method. So I'm just going to click on init and stop and click on ok.
//
//        And you can see that it's written those created those stubs for us.
//
//        All right. So now that they're created in the init method,
//
//        we're going to connect to the database.
//
//        So I'm going to start by doing that.
//
//        Now note here that there's really no need to call the super method
//
//        they don't really do anything, both the super dot init and the super dot stop.
//
//        But we'll leave the super calls there just in case this changes in a future java fx release.
//
//        So to open the database,
//
//        we're going to do data source
//
//        set using the sample dot model package here
//
//        dot get instance dot open.
//
//        And down on the stop method, we'll, again, we'll leave the super dot stop method call there.
//
//        And we'll put data source
//
//        dot get instance dot close.
//
//        All right. So let's run this to make sure it works.
//
//        And we've got an arrow down here.
//
//        And that's because we didn't add the JDBC driver to the class path.
//
//        And I deliberately did that
//
//        because I wanted to show you how the UI still appeared even though we actually got that error.
//
//        Now if we can't connect to the database, we don't want the user interface to appear
//
//        because anything the user tries to do would fail.
//
//        What we want to do is inform the user that there's been a fatal error of some kind
//
//        and then shut down the application.
//
//        Now normally we do this using a pop-up dialog,
//
//        but since this isn't a UI set of videos at this point
//
//        what we're going to do is just really just write a message to the console now.
//
//        The init method will have to check the return value from the open method
//
//        and take appropriate action when necessary.
//
//        So let's go ahead and implement that and then we'll finish off the video.
//
//        I'm going to close this down. So back to our init method,
//
//        we need to just change it a little bit here now.
//
//        And instead of just calling it, we're going to return --
//
//        we're going to make use of the return code. So I'm going to put if
//
//        not data source dot get instance dot open
//
//        and we're going to set out there and we'll just put facial error.
//
//        And again, normally, you'll actually have a pop-up
//
//        dialogue or something of that nature to inform the user of the problem.
//
//        But we're not focusing on the UI here so we're just going to keep it pretty simple
//
//        couldn't connect to database, database.
//
//        Then we'll do a platform capital p dot exit.
//
//        So let's just try running that.
//
//        So this time, we've now got
//
//        the same error message we had previously about not being able to connect to the database,
//
//        but we've now got this photo error couldn't connect to database.
//
//        So it's done what we want it to do. At this point now the user interface doesn't appear
//
//        because we couldn't get access to the database. All right. So let's finish the video here now.