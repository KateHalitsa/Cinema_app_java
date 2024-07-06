package Common.Data;

import java.math.BigDecimal;
import java.util.Date;

public class SessionFilmBase extends BaseObject
{
    public Date sessionTime = null;
    public BigDecimal ticketPrice = null;
    public int hallId;
    public int filmId;
}
