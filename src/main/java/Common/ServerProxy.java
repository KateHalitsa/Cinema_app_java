package Common;

import Common.Data.*;
import Common.FindResults.*;
import Common.Params.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.lang.reflect.Modifier;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerProxy {
    public static final int FUNC_NUM_LOAD_CATEGORIES = 9;
    public static final int FUNC_NUM_FIND_FILMS = 100;
    public static final int FUNC_NUM_FIND_MOVIE_SESSIONS = 101;
    public static final int FUNC_NUM_FIND_TICKETS = 102;
    public static final int FUNC_NUM_UPDATE_MOVIE_SESSION_TICKETS = 103;
    public static final int FUNC_NUM_LOAD_MOVIE_SESSION_INFO = 104;
    public static final int FUNC_NUM_LOAD_HALLS = 105;
    public static final int FUNC_NUM_LOAD_FILM_CATEGORIES = 106;
    public static final int FUNC_NUM_LOAD_ACTORS = 107;
    public static final int FUNC_NUM_SAVE_ACTOR = 108;
    public static final int FUNC_NUM_LOAD_FILM = 109;
    public static final int FUNC_NUM_SAVE_FILM = 110;
    public static final int FUNC_NUM_SAVE_FILM_ACTOR = 111;
    public static final int FUNC_NUM_DELETE_FILM_ACTOR = 112;
    public static final int FUNC_NUM_UPDATE_FILM_CATEGORIES = 113;
    public static final int FUNC_NUM_LOAD_FILM_SESSION = 114;
    public static final int FUNC_NUM_SAVE_FILM_SESSION = 115;

    private Gson gson;
    public ServerProxy()
    {
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.TRANSIENT)
                .setDateFormat("MMM dd, yyyy HH:mm:ss")
                .create();
    }

    private static ServerProxy server = null;
    public static ServerProxy Server()
    {
        if (server == null){
            server = new ServerProxy();
        }
        return server;
    }

    private String callServerFuntion(int numOperation, String inData)
    {
        String outData = "";

        Socket sock = null;
        BufferedReader in = null;
        //     BufferedWriter out = null;
        InputStream is = null;
        OutputStream os = null;
        try {
            sock = new Socket("localhost", 4004);
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            //  out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));

            is = sock.getInputStream(); // входной поток для чтения данных
            os = sock.getOutputStream();// выходной поток для записи данных

            String clearString = inData.replace("\n","")+"\n";

            os.write(numOperation);
            os.write(clearString.getBytes());
            os.flush();

            outData = in.readLine();

        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            try {
                os.close();//закрытие выходного потока
                is.close();//закрытие входного потока
                //out.close();
                in.close();
                sock.close();//закрытие сокета, выделенного для работы с сервером
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        return outData;
    }
    public ArrayList<Category> loadCategories(LoadCategoriesParam param)
    {
        String inData = gson.toJson(param);

        String outData = callServerFuntion(FUNC_NUM_LOAD_CATEGORIES, inData);

        java.lang.reflect.Type collectionType = new TypeToken<ArrayList<Category>>(){}.getType();
        return gson.fromJson( outData , collectionType);
    }

    public List<FindFilmsResult> findFilms(FindFilmsParam param) {

        String inData = gson.toJson(param);
        String outData = callServerFuntion(FUNC_NUM_FIND_FILMS, inData);
        java.lang.reflect.Type collectionType = new TypeToken<List<FindFilmsResult>>(){}.getType();

        JsonReader reader = new JsonReader(new StringReader(outData));
        reader.setLenient(true);
        return gson.fromJson(reader, collectionType);

    }

    public List<FindMovieSessionResult> findMovieSessions(FindMovieSessionsParam param) {

        String inData = gson.toJson(param);
        String outData = callServerFuntion(FUNC_NUM_FIND_MOVIE_SESSIONS, inData);
        java.lang.reflect.Type collectionType = new TypeToken<List<FindMovieSessionResult>>(){}.getType();
        return gson.fromJson(outData , collectionType);
    }

    public ArrayList<TicketBase> findTickets(FindTicketsParam param) {

        String inData = gson.toJson(param);
        String outData = callServerFuntion(FUNC_NUM_FIND_TICKETS, inData);
        java.lang.reflect.Type collectionType = new TypeToken<ArrayList<TicketBase>>(){}.getType();
        return gson.fromJson(outData , collectionType);
    }

    public ArrayList<TicketBase> updateMovieSessionTickets(UpdateMovieSessionTicketsParam param)
    {
        String inData = gson.toJson(param);
        String outData = callServerFuntion(FUNC_NUM_UPDATE_MOVIE_SESSION_TICKETS, inData);
        java.lang.reflect.Type collectionType = new TypeToken<ArrayList<TicketBase>>(){}.getType();
        return gson.fromJson(outData, collectionType);
    }

    public LoadMovieSessionInfoResult loadMovieSessionInfo(FindTicketsParam param)
    {
        String inData = gson.toJson(param);
        String outData = callServerFuntion(FUNC_NUM_LOAD_MOVIE_SESSION_INFO, inData);
        return gson.fromJson(outData , LoadMovieSessionInfoResult.class);
    }

    public ArrayList<HallBase> loadHalls()
    {
        String inData = ""; // gson.toJson(param);
        String outData = callServerFuntion(FUNC_NUM_LOAD_HALLS, inData);

        java.lang.reflect.Type collectionType = new TypeToken<ArrayList<HallBase>>(){}.getType();
        return gson.fromJson(outData , collectionType);
    }

    public ArrayList<Integer> loadFilmCategories(int filmId)
    {
        String inData = Integer.toString(filmId);
        String outData = callServerFuntion(FUNC_NUM_LOAD_FILM_CATEGORIES, inData);

        java.lang.reflect.Type collectionType = new TypeToken<ArrayList<Integer>>(){}.getType();
        return gson.fromJson(outData , collectionType);
    }

    public ArrayList<ActorBase> loadActors(LoadActorsParam param)
    {
        String inData = gson.toJson(param);
        String outData = callServerFuntion(FUNC_NUM_LOAD_ACTORS, inData);

        java.lang.reflect.Type collectionType = new TypeToken<ArrayList<ActorBase>>(){}.getType();
        return gson.fromJson(outData , collectionType);
    }

    public ActorBase saveActor(ActorBase actor)
    {
        String inData = gson.toJson(actor);
        String outData = callServerFuntion(FUNC_NUM_SAVE_ACTOR, inData);
        return gson.fromJson(outData, ActorBase.class);
    }

    public FilmBase loadFilm(int filmId)
    {
        String inData = Integer.toString(filmId);
        String outData = callServerFuntion(FUNC_NUM_LOAD_FILM, inData);
        return gson.fromJson(outData, FilmBase.class);
    }

    public FilmBase saveFilm(FilmBase film)
    {
        String inData = gson.toJson(film);
        String outData = callServerFuntion(FUNC_NUM_SAVE_FILM, inData);
        return gson.fromJson(outData, FilmBase.class);
    }

    public void saveFilmActor(FilmActorBase filmActor)
    {
        String inData = gson.toJson(filmActor);
        String outData = callServerFuntion(FUNC_NUM_SAVE_FILM_ACTOR, inData);
    }

    public void deleteFilmActor(FilmActorBase filmActor)
    {
        String inData = gson.toJson(filmActor);
        String outData = callServerFuntion(FUNC_NUM_DELETE_FILM_ACTOR, inData);
    }

    public void updateFilmCategories(UpdateFilmCategoriesParam param)
    {
        String inData = gson.toJson(param);
        String outData = callServerFuntion(FUNC_NUM_UPDATE_FILM_CATEGORIES, inData);
    }

    public SessionFilmBase loadFilmSession(int filmSessionId)
    {
        String inData = Integer.toString(filmSessionId);
        String outData = callServerFuntion(FUNC_NUM_LOAD_FILM_SESSION, inData);
        return gson.fromJson(outData, SessionFilmBase.class);
    }

    public SessionFilmBase saveFilmSession(SessionFilmBase filmSession)
    {
        String inData = gson.toJson(filmSession);
        String outData = callServerFuntion(FUNC_NUM_SAVE_FILM_SESSION, inData);
        return gson.fromJson(outData, SessionFilmBase.class);
    }


}
