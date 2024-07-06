package Server.src.ServerWork;

import Common.Data.*;
import Common.FindResults.*;
import Common.Params.*;
import Common.ServerProxy;
import java.io.*;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import Server.src.Database.MyDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


//import static sun.security.util.KnownOIDs.Data;

public class ServerWork {
    private BufferedReader in;
    private PrintWriter out;
    private MyDatabase database;

    private Gson gson;

 private OutputStream os;
    public ServerWork (BufferedReader in, PrintWriter out, OutputStream os, MyDatabase database){
        this.in = in;
        this.out = out;
        this.os=os;
        this.database = database;

        gson = new GsonBuilder()
                .setPrettyPrinting()
                .excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.TRANSIENT)
                .setDateFormat("MMM dd, yyyy HH:mm:ss")
                .create();
    }

    public void getId (int idOperation) throws IOException, SQLException{
        String str;
        switch(idOperation){
          case 1:
                signingIn();
                break;
            case 2:
                registrationUser();
                break;
            /* */ case 3:
                outputUserTable();
                break;
            case 4:
                str=in.readLine();
                database.insert(str);
                break;
            case 5:
                //str=in.readLine();
                database.update(in.readLine());
                break;
            case 6:
                deleteActor();
// database.delete(in.readLine());
                break;
         case 7:
                outputActorTable();
                break;
           case 8:
               deleteCategory();
                break;
              case ServerProxy.FUNC_NUM_LOAD_CATEGORIES:
                  outputCategoryTable();
                break;
             case 10:
                 outputHallTable();
                break;
           case 11:
               deleteHall();
                break;
            /*  case 12:
               database.delete(in.readLine());
                break;*/
            case ServerProxy.FUNC_NUM_FIND_FILMS:
                findFilms();
                break;

            case ServerProxy.FUNC_NUM_FIND_MOVIE_SESSIONS:
                findMovieSessions();
                break;

            case ServerProxy.FUNC_NUM_FIND_TICKETS:
                findTickets();
                break;

            case ServerProxy.FUNC_NUM_UPDATE_MOVIE_SESSION_TICKETS:
                updateUpdateMovieSessionTickets();
                break;

            case ServerProxy.FUNC_NUM_LOAD_MOVIE_SESSION_INFO:
                loadMovieSessionInfoResult();
                break;

            case ServerProxy.FUNC_NUM_LOAD_HALLS:
                loadHalls();
                break;

            case ServerProxy.FUNC_NUM_LOAD_FILM_CATEGORIES:
                loadFilmCategories();
                break;

            case ServerProxy.FUNC_NUM_LOAD_ACTORS:
                loadActors();
                break;

            case ServerProxy.FUNC_NUM_SAVE_ACTOR:
                saveActor();
                break;

            case ServerProxy.FUNC_NUM_LOAD_FILM:
                loadFilm();
                break;

            case ServerProxy.FUNC_NUM_SAVE_FILM:
                saveFilm();
                break;

            case ServerProxy.FUNC_NUM_SAVE_FILM_ACTOR:
                saveFilmActor();
                break;

            case ServerProxy.FUNC_NUM_DELETE_FILM_ACTOR:
                deleteFilmActor();
                break;

            case ServerProxy.FUNC_NUM_UPDATE_FILM_CATEGORIES:
                updateFilmCategories();
                break;

            case ServerProxy.FUNC_NUM_LOAD_FILM_SESSION:
                loadFilmSession();
                break;

            case ServerProxy.FUNC_NUM_SAVE_FILM_SESSION:
                saveFilmSession();
                break;
        }
    }
    PreparedStatement ps = null;
    ResultSet result = null;
    Connection con=null;
    private void signingIn() throws IOException, SQLException{
        String str = in.readLine();

         con = database.getConnection();

        User user =  gson.fromJson( str , User.class);

        String query = "select * from user where name = ? and pass = ?";
        ps = con.prepareStatement(query);
        ps.setString(1, user.getLogin());
        ps.setString(2,user.getPassword());
        result = ps.executeQuery();
        result.next();
        String baseName=result.getString("name");
        String basePass=result.getString("pass");
        if (baseName.equals(user.getLogin())&&basePass.equals(user.getPassword())){
      // result = database.select(ps);
        String query1 = "select id from user where name=?";

        ps = con.prepareStatement(query1);
        ps.setString(1, user.getLogin());

        result2 = ps.executeQuery();
        result2.next();

        int id=result2.getInt("id");
        String query2 = "select * from user_role where user_id=?";
        ps = con.prepareStatement(query2);
        ps.setInt(1, id);

        result = ps.executeQuery();
        if(result.next()){
           os.write(result.getInt("role_id"));
        }

        }else{
            os.write(-1);
        }
    }
int st;
    ResultSet result2=null;
    private void registrationUser() throws IOException, SQLException{
        String str = in.readLine();

        con = database.getConnection();

        User user = gson.fromJson( str , User.class);

        String query0 = "select id from user where name=?";

        ps = con.prepareStatement(query0);
        ps.setString(1, user.getLogin());

      //  result = ps.executeQuery();
        result = ps.executeQuery();

        boolean answer=false;
        while(result.next())
        {
            os.write(-1);
            answer=true;
        }

            if(!answer){/**/

                String query1 = "insert into user(name,pass) " + "values(?,?)";

                ps = con.prepareStatement(query1);
                ps.setString(1, user.getLogin());
                ps.setString(2, user.getPassword());

                st = ps.executeUpdate();
                String query2 = "select id from user where name=?";

                ps = con.prepareStatement(query2);
                ps.setString(1, user.getLogin());

                result2 = ps.executeQuery();
                result2.next();
                int id=result2.getInt("id");

                String query3= "insert into user_role(user_id,role_id) " + "values(?,?)";
                ps = con.prepareStatement(query3);
                ps.setInt(1, id);
                ps.setInt(2, user.getStatus());
                st = ps.executeUpdate();
                // result = database.select(ps);

                os.write(user.getStatus());
            }

    }
    private void outputActorTable()throws IOException, SQLException{
        ResultSet result = database.select("SELECT * FROM actor");
        ResultSet result1=result;

        ArrayList<Actor> actors ;

        // Java objects to String

        int count = 0;
        actors=new ArrayList<Actor>();

        if( result.next()/*result.first()*/){

            do{   String fname=result1.getString("first_name");
                Actor actor = new Actor("","");
                actor.setName(fname);
                String lname=result1.getString("last_name");
                actor.setLastName(lname);
                actors.add(actor);
                count++;

            }while(result.next());
        }
       // out.println(Integer.toString(count));


        /*for(int i = 0; i < count; i++){

            String fname=result1.getString("first_name");
            actors.get(i).setName(fname);
            String lname=result1.getString("last_name");
            actors.get(i).setLastName(lname);
            result1.next();

        }*/

        String json = gson.toJson(actors).replace("\n","")+"\n"/**/;
        out.print(json);

        //переменнная,в которую будут записываться числа делящиеся на 3
        byte[] bytes = new byte[3000];



        bytes = json.getBytes();
        os.write(bytes); // отправляем клиенту информацию
    }

    private void deleteActor() throws IOException, SQLException{
        String str = in.readLine();

        con = database.getConnection();

        //Type collectionType = new TypeToken<Actor>(){}.getType();
        Actor actor = gson.fromJson( str , Actor.class);

        String query0 = "select actor_id from actor where first_name=? AND last_name=?";

        ps = con.prepareStatement(query0);
        ps.setString(1, actor.getName());
        ps.setString(2, actor.lastName());

        //  result = ps.executeQuery();
        result = ps.executeQuery();
        result.next();
        int id=result.getInt("actor_id");

        String query1 = "select actor_id from film_actor where actor_id=?";
        ps.setInt(1,id);
        result2 = ps.executeQuery();
        //result2.next();
        while(result2.next())
        {
            String query2 = "DELETE  from film_actor where actor_id='"+id+"'";
            database.delete(query2);

        }
        String query3 = "DELETE  from actor where actor_id='"+id+"'";
        database.delete(query3);

    }
    private void deleteCategory() throws IOException, SQLException{
        String str = in.readLine();

        con = database.getConnection();

        Category category = gson.fromJson( str , Category.class);

        String query0 = "select category_id from category where name=?";

        ps = con.prepareStatement(query0);
        ps.setString(1, category.name);


        //  result = ps.executeQuery();
        result = ps.executeQuery();
        result.next();
        int id=result.getInt("category_id");

        String query1 = "select category_id from film_category where category_id=?";
        ps.setInt(1,id);
        result2 = ps.executeQuery();
        //result2.next();
        while(result2.next())
        {
            String query2 = "DELETE  from film_category where category_id='"+id+"'";
            database.delete(query2);

        }
        String query3 = "DELETE  from category where category_id='"+id+"'";
        database.delete(query3);

    }
    private void outputCategoryTable()throws IOException, SQLException
    {
        String str = in.readLine();
        LoadCategoriesParam param = gson.fromJson(str, LoadCategoriesParam.class);

        String where = "";
        if (param.filmId > 0)
        {
            where = addWhere(where, "c.category_id in (select fc.category_id from film_category fc where fc.film_id = " + param.filmId + " )");
        }
        if (!where.isEmpty())
        {
            where = " where " + where;
        }
        ResultSet result = database.select("SELECT * FROM category c" + where + " order by c.name");

        ArrayList<Category> categories = new ArrayList<Category>();

        if( result.next()/*result.first()*/){

            do{
                Category category = new Category();
                category.id = result.getInt("category_id");
                category.name = result.getString("name");
                categories.add(category);

            }while(result.next());
        }

        String json = gson.toJson(categories).replace("\n","")+"\n"/**/;
        out.print(json);

        //переменнная,в которую будут записываться числа делящиеся на 3
        byte[] bytes = json.getBytes();
        os.write(bytes); // отправляем клиенту информацию
    }

    private void loadActors()throws IOException, SQLException
    {
        String str = in.readLine();
        LoadActorsParam param = gson.fromJson(str, LoadActorsParam.class);

        String where = "";
        if (!param.name.isEmpty())
        {
            where = addWhere(where, "first_name like '%" + param.name + "%'");
        }
        if (!param.lastName.isEmpty())
        {
            where = addWhere(where, "last_name like '%" + param.lastName + "%'");
        }
        if (param.withoutActorsFromFilmId > 0)
        {
            where = addWhere(where, "actor_id not in (select fa.actor_id from film_actor fa where fa.film_id = " + param.withoutActorsFromFilmId + ")");
        }
        if (param.actorsForFilmId > 0)
        {
            where = addWhere(where, "actor_id in (select fa.actor_id from film_actor fa where fa.film_id = " + param.actorsForFilmId + ")");
        }

        if (!where.isEmpty())
        {
            where = " where " + where;
        }
        ResultSet result = database.select("SELECT * FROM actor" + where + " order by first_name, last_name");

        ArrayList<ActorBase> actors = new ArrayList<ActorBase>();

        if( result.next()/*result.first()*/){

            do{
                ActorBase actor = new ActorBase();
                actor.id = result.getInt("actor_id");
                actor.name = result.getString("first_name");
                actor.lastName = result.getString("last_name");
                actors.add(actor);
            }while(result.next());
        }

        String json = gson.toJson(actors).replace("\n","")+"\n"/**/;
        out.print(json);

        //переменнная,в которую будут записываться числа делящиеся на 3
        byte[] bytes = json.getBytes();
        os.write(bytes); // отправляем клиенту информацию
    }

    private void saveActor()throws IOException, SQLException{
        String str = in.readLine();
        ActorBase actor = gson.fromJson(str, ActorBase.class);

        con = database.getConnection();
        if (actor.id == 0)
        {
            String sql = "insert into actor (first_name, last_name) values(?,?)";
            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, actor.name);
            ps.setString(2, actor.lastName);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            actor.id = rs.getInt(1);  // Return auto-incremented Id
        }
        else
        {
            String sql = "UPDATE actor SET first_name = ?, last_name = ? WHERE actor_id = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, actor.name);
            ps.setString(2, actor.lastName);
            ps.setInt(3, actor.id);
            ps.executeUpdate();
        }

        String json = gson.toJson(actor).replace("\n","") + "\n";
        out.print(json);
        os.write(json.getBytes()); // отправляем клиенту информацию
    }

    private void outputHallTable()throws IOException, SQLException{
        ResultSet result = database.select("SELECT * FROM cinema_room");
        ResultSet result1=result;

        ArrayList<Hall> halls ;

        // Java objects to String

        int count = 0;
        halls=new ArrayList<Hall>();

        if( result.next()/*result.first()*/){

            do{   String fname=result1.getString("name");
                int r=result1.getInt("rows");
                int s=result1.getInt("seats");
                Hall hall = new Hall("",0,0);
                hall.setName(fname);
                hall.setRows(r);
                hall.setSeats(s);
                halls.add(hall);
                count++;

            }while(result.next());
        }

        String json = gson.toJson(halls).replace("\n","") + "\n";
        out.print(json);
        os.write(json.getBytes()); // отправляем клиенту информацию
    }

    private void loadHalls() throws IOException, SQLException{

        ResultSet result = database.select("SELECT * FROM cinema_room order by name");

        ArrayList<HallBase> halls = new ArrayList<HallBase>();
        if( result.next()/*result.first()*/)
        {
            do{
                HallBase hall = new HallBase();
                hall.id = result.getInt("id");
                hall.name = result.getString("name");
                hall.rows = result.getInt("rows");
                hall.seats = result.getInt("seats");
                halls.add(hall);
            }while(result.next());
        }

        String json = gson.toJson(halls).replace("\n","") + "\n";
        out.print(json);
        os.write(json.getBytes()); // отправляем клиенту информацию

    }

    private void deleteHall() throws IOException, SQLException{
        String str = in.readLine();

        con = database.getConnection();

        Hall hall = gson.fromJson( str , Hall.class);

        String query0 = "select id from cinema_room where name=?";

        ps = con.prepareStatement(query0);
        ps.setString(1, hall.getName());


        //  result = ps.executeQuery();
        result = ps.executeQuery();
        result.next();
        int id=result.getInt("id");

        String query1 = "select cinema_room_id from movie_session where cinema_room_id=?";
        ps.setInt(1,id);
        result2 = ps.executeQuery();
        //result2.next();
        while(result2.next())
        {
            String query2 = "DELETE  from movie_session where cinema_room_id='"+id+"'";
            database.delete(query2);

        }
        String query3 = "DELETE  from cinema_room where id='"+id+"'";
        database.delete(query3);

    }
    private void  outputUserTable()throws IOException, SQLException{
        ResultSet result = database.select("SELECT * FROM user");
        con = database.getConnection();
        ResultSet result1=result;

        ArrayList<User> users ;

        // Java objects to String

        int count = 0;
        users=new ArrayList<User>();


        if( result1.next()/*result.first()*/){
            do{
                String query1 = "select role_id from user_role where user_id=?";

            int user_id=result.getInt("id");
            ps = con.prepareStatement(query1);
            ps.setInt(1,user_id);
            result2 = ps.executeQuery();
            ResultSet result3=result2;
              User user = new User("","",0);
if(result3.next()){


        String log=result.getString("name");
        String pass=result.getString("pass");
        int role=result2.getInt("role_id");

        user.setLogin(log);
        user.setPassword(pass);
        user.setStatus(role);
        users.add(user);
        count++;

}


            }while(result.next());
        }

        String json = gson.toJson(users).replace("\n","")+"\n";
        out.print(json);

        //переменнная,в которую будут записываться числа делящиеся на 3
        byte[] bytes = new byte[3000];



        bytes = json.getBytes();
        os.write(bytes); // отправляем клиенту информацию
    }



    private String addWhere(String where, String whereParam)
    {
        if (!Objects.equals(where, ""))
        {
            where = where + " AND ";
        }
        return where + whereParam;
    }

    private ArrayList<String> filmCategoriyNames(int filmId) throws SQLException {
        ResultSet result = database.select(
          "SELECT c.name FROM category c, film_category fc " +
                  "where c.category_id = fc.category_id and film_id = " + filmId);
        ArrayList<String> names = new ArrayList<>();
        if( result.next()/*result.first()*/){

            do{
                names.add(result.getString("name"));
            }while(result.next());
        }
        return names;
    }

    private ArrayList<Integer> filmCategoriyIds(int filmId) throws SQLException
    {
        ResultSet result = database.select(
                "SELECT category_id FROM film_category where film_id = " + filmId);
        ArrayList<Integer> idArray = new ArrayList<>();
        if( result.next()/*result.first()*/){

            do{
                idArray.add(result.getInt("category_id"));
            }while(result.next());
        }
        return idArray;
    }

    private void loadFilmCategories() throws IOException, SQLException {
        String str = in.readLine();
        int filmId = Integer.parseInt(str);
        ArrayList<Integer> idArray = filmCategoriyIds(filmId);
        String outStr = gson.toJson(idArray).replace("\n","")+"\n";;
        out.print(outStr);
        os.write(outStr.getBytes());
        os.flush();
    }

    private ArrayList<String> filmActorNames(int filmId) throws SQLException {
        ResultSet result = database.select(
                "SELECT a.first_name, a.last_name FROM actor a, film_actor fa " +
                        "where a.actor_id = fa.actor_id and fa.film_id = " + filmId + " order by first_name, last_name");
        ArrayList<String> names = new ArrayList<>();
        if( result.next()/*result.first()*/){

            do{
                String name =  result.getString("first_name") + " " + result.getString("last_name");
                names.add(name.trim());
            }while(result.next());
        }
        return names;
    }

    void findFilms() throws IOException, SQLException
    {
        String str = in.readLine();
        FindFilmsParam param = gson.fromJson(str, FindFilmsParam.class);

        String where = "";
        if (param.filmId > 0)
        {
            where = addWhere(where, "film_id = " + param.filmId);
        }
        if (param.filmName != "")
        {
            where = addWhere(where, "title like '%" + param.filmName + "%'");
        }
        if (param.fromYear > 0)
        {
            where = addWhere(where, "release_year >= " + param.fromYear);
        }
        if (param.toYear > 0)
        {
            where = addWhere(where, "release_year <= " + param.toYear);
        }
        if (param.filmCategory > 0)
        {
            where = addWhere(where,
                    "exists(select * from film_category fc where fc.film_id = film.film_id and fc.category_id =" + param.filmCategory + ")");
        }

        if (where != "")
        {
            where = " WHERE " + where;
        }

        String nearestPast = "(select max(session_time) from movie_session where film_film_id = film_id and session_time <= NOW()) nearest_past_session";
        String nearestFuture = "(SELECT Min(session_time) FROM movie_session WHERE film_film_id = film_id AND session_time >= NOW()) nearest_future_session";
        String sql = "SELECT *," + nearestPast + ", " + nearestFuture +
        " FROM film" + where + " order by title";

        ResultSet result = database.select(sql);

        FindFilmsResult film;
        ArrayList<FindFilmsResult> films = new ArrayList<>();
        if( result.next()/*result.first()*/){

            do{
                film = new FindFilmsResult();
                film.id = result.getInt("film_id");
                film.title = result.getString("title");
                film.year = result.getInt("release_year");
                film.director = result.getString("director");
                if (param.filmId > 0)
                {
                    film.description = result.getString("description");
                }
                film.length = result.getInt("length");
                film.nearestFutureSessionDate = result.getTimestamp("nearest_future_session");
                film.nearestPastSessionDate = result.getTimestamp("nearest_past_session");
                films.add(film);
            }while(result.next());
        }

        for (int i = 0; i < films.size(); i++)
        {
            film = films.get(i);
            film.categories = filmCategoriyNames(film.id);
            film.actors = filmActorNames(film.id);
        }

        String outStr = gson.toJson(films).replace("\n","")+"\n";;
        out.print(outStr);
        os.write(outStr.getBytes());
        os.flush();
    }

    void findMovieSessions() throws IOException, SQLException
    {
        String str = in.readLine();
        FindMovieSessionsParam param = gson.fromJson(str, FindMovieSessionsParam.class);

        java.text.SimpleDateFormat sqlDateFormat =
                new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String where = "film_film_id = " + param.filmId;
        if (param.fromTime.length() > 0)
        {
            where = addWhere(where, "TIME(session_time) >= '" + param.fromTime + "'");
        }
        if (param.toTime.length() > 0)
        {
            where = addWhere(where, "TIME(session_time) <= '" + param.toTime + "'");
        }
        if (param.fromDate != null)
        {
            where = addWhere(where, "session_time >= '" + sqlDateFormat.format(param.fromDate) + "'");
        }
        if (param.toDate != null)
        {
            Calendar c = Calendar.getInstance();
            c.setTime(param.toDate);
            c.add(Calendar.DATE, 1); // Increament One Day
            java.util.Date toDate = c.getTime();
            where = addWhere(where, "session_time < '" + sqlDateFormat.format(toDate) + "'");
        }

        if (where != "")
        {
            where = " WHERE " + where;
        }

        String roomNameSQL = "(SELECT cr.name FROM cinema_room cr WHERE cr.id = ms.cinema_room_id) room_name ";
        String freeSeatsSQL =
                "(SELECT cr.rows * cr.seats FROM cinema_room cr WHERE cr.id = ms.cinema_room_id)" +
                " - (SELECT COUNT(*) FROM ticket t WHERE t.movie_session_id = ms.id) free_seats ";

        String sql =
                "SELECT ms.id id, session_time, ticket_price, " + roomNameSQL + ", " + freeSeatsSQL +
                "FROM movie_session ms" +
                 where +
                " order by session_time desc";

        ResultSet result = database.select(sql);

        FindMovieSessionResult movieSession;
        ArrayList<FindMovieSessionResult> movieSessions = new ArrayList<>();
        if( result.next()/*result.first()*/){

            do{
                movieSession = new FindMovieSessionResult();
                movieSession.movieSessionId = result.getInt("id");
                movieSession.sessionTime = result.getTimestamp("session_time");
                movieSession.ticketPrice = result.getBigDecimal("ticket_price");
                movieSession.roomName = result.getString("room_name");
                movieSession.freeSeats = result.getInt("free_seats");
                movieSessions.add(movieSession);
            }while(result.next());
        }

        String outStr = gson.toJson(movieSessions).replace("\n","")+"\n";;
        out.print(outStr);
        os.write(outStr.getBytes());
        os.flush();
    }

    private ArrayList<TicketBase> loadTicketsFortSession(int movieSessionId) throws SQLException {
        String sql = "SELECT * FROM ticket where movie_session_id = " + movieSessionId;
        ResultSet result = database.select(sql);

        TicketBase ticket;
        ArrayList<TicketBase> tickets = new ArrayList<>();
        if( result.next()/*result.first()*/){

            do{
                ticket = new TicketBase();
                ticket.id = result.getInt("id");
                ticket.row = result.getInt("row");
                ticket.seat = result.getInt("seat");
                ticket.movieSessionId = result.getInt("movie_session_id");
                ticket.userId = result.getInt("user_id");
                tickets.add(ticket);
            }while(result.next());
        }
        return tickets;
    }
    void findTickets() throws IOException, SQLException {
        String str = in.readLine();
        FindTicketsParam param = gson.fromJson(str, FindTicketsParam.class);

        ArrayList<TicketBase> tickets = loadTicketsFortSession(param.movieSessionId);

        String outStr = gson.toJson(tickets).replace("\n","")+"\n";;
        out.print(outStr);
        os.write(outStr.getBytes());
        os.flush();
    }

    void updateUpdateMovieSessionTickets() throws IOException, SQLException {
        String str = in.readLine();
        UpdateMovieSessionTicketsParam param = gson.fromJson(str, UpdateMovieSessionTicketsParam.class);

        Connection connection = database.getConnection();
        if (!param.deleteTickets.isEmpty())
        {
            String sql = "delete from ticket where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < param.deleteTickets.size(); i++)
            {
                preparedStatement.setInt(1, param.deleteTickets.get(i));
                preparedStatement.executeUpdate();
            }
        }
        if (!param.insertTickets.isEmpty())
        {
            String sql = "INSERT INTO ticket (movie_session_id, user_id, `row`, seat) VALUES(?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, param.movieSessionId);
            preparedStatement.setInt(2, param.userId);
            for (int i = 0; i < param.insertTickets.size(); i++)
            {
                UpdateMovieSessionTicketsParam.SeatRow seatRow = param.insertTickets.get(i);
                preparedStatement.setInt(3, seatRow.Row);
                preparedStatement.setInt(4, seatRow.Seat);
                preparedStatement.executeUpdate();
            }
        }

        ArrayList<TicketBase> tickets;
        if (param.needReturnAllTickets)
        {
            tickets = loadTicketsFortSession(param.movieSessionId);
        }
        else
        {
            tickets = new ArrayList<>();
        }

        String outStr = gson.toJson(tickets).replace("\n","")+"\n";;
        out.print(outStr);
        os.write(outStr.getBytes());
        os.flush();
    }

    void loadMovieSessionInfoResult()throws IOException, SQLException
    {
        String str = in.readLine();
        FindTicketsParam param = gson.fromJson(str, FindTicketsParam.class);

        LoadMovieSessionInfoResult functionResult = new LoadMovieSessionInfoResult();
        functionResult.ticktes = loadTicketsFortSession(param.movieSessionId);

        String sql = "SELECT *, " +
                "(SELECT f.title FROM film f WHERE f.film_id = ms.film_film_id) film_name, " +
                "(SELECT cr.name FROM cinema_room cr WHERE cr.id = ms.cinema_room_id) room_name, " +
                "(SELECT cr.rows FROM cinema_room cr WHERE cr.id = ms.cinema_room_id) room_rows, " +
                "(SELECT cr.seats FROM cinema_room cr WHERE cr.id = ms.cinema_room_id) room_sets " +
                "FROM movie_session ms where ms.id = " + param.movieSessionId;
        ResultSet result = database.select(sql);

        if( result.next()/*result.first()*/)
        {
            functionResult.sessionId = result.getInt("id");
            functionResult.ticketPrice  = result.getBigDecimal("ticket_price");
            functionResult.sessionTime = result.getTimestamp("session_time");
            functionResult.filmId = result.getInt("film_film_id");
            functionResult.roomId = result.getInt("cinema_room_id");
            functionResult.filmName = result.getString("film_name");
            functionResult.roomName = result.getString("room_name");
            functionResult.roomRows = result.getInt("room_rows");
            functionResult.roomSeats = result.getInt("room_sets");
        }

        String outStr = gson.toJson(functionResult).replace("\n","")+"\n";;
        out.print(outStr);
        os.write(outStr.getBytes());
        os.flush();
    }

    void loadFilm() throws IOException, SQLException
    {
        String str = in.readLine();
        int filmID = Integer.parseInt(str);

        String sql = "select * from film where film_id = " + filmID;
        ResultSet result = database.select(sql);

        FilmBase film = new FilmBase();
        if( result.next()/*result.first()*/)
        {
            film.id = result.getInt("film_id");
            film.title = result.getString("title");
            film.description = result.getString("description");
            film.year = result.getInt("release_year");
            film.length = result.getInt("length");
            film.director = result.getString("director");
        }

        String outStr = gson.toJson(film).replace("\n","")+"\n";;
        out.print(outStr);
        os.write(outStr.getBytes());
        os.flush();

    }

    private void saveFilm()throws IOException, SQLException{
        String str = in.readLine();
        FilmBase film = gson.fromJson(str, FilmBase.class);

        con = database.getConnection();
        if (film.id == 0)
        {
            String sql = "insert into film (title, description, director, release_year, length, video_url) values(?,?,?,?,?,?)";
            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, film.title);
            ps.setString(2, film.description);
            ps.setString(3, film.director);
            ps.setInt(4, film.year);
            ps.setInt(5, film.length);
            ps.setString(6, ""); // video_url ???
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            film.id = rs.getInt(1);  // Return auto-incremented Id
        }
        else
        {
            String sql = "UPDATE film SET title = ?, description = ?, director = ?, release_year = ?, length = ?  WHERE film_id = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, film.title);
            ps.setString(2, film.description);
            ps.setString(3, film.director);
            ps.setInt(4, film.year);
            ps.setInt(5, film.length);
            ps.setInt(6, film.id);
            ps.executeUpdate();
        }

        String json = gson.toJson(film).replace("\n","") + "\n";
        out.print(json);
        os.write(json.getBytes()); // отправляем клиенту информацию
    }

    void loadFilmSession() throws IOException, SQLException
    {
        String str = in.readLine();
        int filmSessionID = Integer.parseInt(str);

        String sql = "select * from movie_session where id = " + filmSessionID;
        ResultSet result = database.select(sql);

        SessionFilmBase filmSession = new SessionFilmBase();
        if( result.next()/*result.first()*/)
        {
            filmSession.id = result.getInt("id");
            filmSession.sessionTime = result.getTimestamp("session_time");
            filmSession.ticketPrice = result.getBigDecimal("ticket_price");
            filmSession.hallId = result.getInt("cinema_room_id");
            filmSession.filmId = result.getInt("film_film_id");
        }

        String outStr = gson.toJson(filmSession).replace("\n","")+"\n";;
        out.print(outStr);
        os.write(outStr.getBytes());
        os.flush();
    }

    private void saveFilmSession()throws IOException, SQLException{
        String str = in.readLine();
        SessionFilmBase filmSession = gson.fromJson(str, SessionFilmBase.class);

        con = database.getConnection();
        if (filmSession.id == 0)
        {
            String sql = "insert into movie_session (session_time, ticket_price, cinema_room_id, film_film_id) values (?, ?, ?, ?);";
            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setTimestamp(1, new Timestamp(filmSession.sessionTime.getTime()));
            ps.setBigDecimal(2, filmSession.ticketPrice);
            ps.setInt(3, filmSession.hallId);
            ps.setInt(4, filmSession.filmId);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            filmSession.id = rs.getInt(1);  // Return auto-incremented Id
        }
        else
        {
            String sql = "UPDATE movie_session SET session_time = ?, ticket_price = ?, cinema_room_id = ?, film_film_id = ? WHERE id = ?";
            ps = con.prepareStatement(sql);
            ps.setTimestamp(1, new Timestamp(filmSession.sessionTime.getTime()));
            ps.setBigDecimal(2, filmSession.ticketPrice);
            ps.setInt(3, filmSession.hallId);
            ps.setInt(4, filmSession.filmId);
            ps.setInt(5, filmSession.id);
            ps.executeUpdate();
        }

        String json = gson.toJson(filmSession).replace("\n","") + "\n";
        out.print(json);
        os.write(json.getBytes()); // отправляем клиенту информацию
    }


    private void saveFilmActor()throws IOException, SQLException{
        String str = in.readLine();
        FilmActorBase filmActor = gson.fromJson(str, FilmActorBase.class);

        con = database.getConnection();

        String sql = "insert into film_actor (film_id, actor_id) values(?,?)";
        ps = con.prepareStatement(sql);
        ps.setInt(1, filmActor.filmId);
        ps.setInt(2, filmActor.actorId);
        ps.executeUpdate();


        String json = "\n";
        out.print(json);
        os.write(json.getBytes()); // отправляем клиенту информацию
    }

    private void deleteFilmActor() throws IOException, SQLException{
        String str = in.readLine();
        FilmActorBase filmActor = gson.fromJson(str, FilmActorBase.class);

        con = database.getConnection();

        String sql = "DELETE FROM film_actor WHERE film_id = ? AND actor_id = ?";
        ps = con.prepareStatement(sql);
        ps.setInt(1, filmActor.filmId);
        ps.setInt(2, filmActor.actorId);
        ps.executeUpdate();

        String json = "\n";
        out.print(json);
        os.write(json.getBytes()); // отправляем клиенту информацию
    }

    private void updateFilmCategories() throws IOException, SQLException {
        String str = in.readLine();
        UpdateFilmCategoriesParam param = gson.fromJson(str, UpdateFilmCategoriesParam.class);

        con = database.getConnection();

        if (param.addedCategoryIds.size() > 0)
        {
            String sql = "INSERT INTO film_category (film_id, category_id) VALUES (?, ?)";
            ps = con.prepareStatement(sql);
            ps.setInt(1, param.filmId);
            for(int i = 0; i < param.addedCategoryIds.size(); i++)
            {
                ps.setInt(2, param.addedCategoryIds.get(i));
                ps.executeUpdate();
            }
        }
        if (param.deletedCategoryIds.size() > 0)
        {
            String sql = "DELETE FROM film_category WHERE film_id = ? AND category_id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, param.filmId);
            for(int i = 0; i < param.deletedCategoryIds.size(); i++)
            {
                ps.setInt(2, param.deletedCategoryIds.get(i));
                ps.executeUpdate();
            }
        }

        String json = "\n";
        out.print(json);
        os.write(json.getBytes()); // отправляем клиенту информацию
    }

}