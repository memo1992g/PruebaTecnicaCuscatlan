package backend.apiscart.component.util;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.TimeZone;

public class DateUtils {
	
	public static String formaterDate() {
		 // Especificar la zona horaria de El Salvador
        ZoneId zonaElSalvador = ZoneId.of("America/El_Salvador");

        // Obtener la fecha y hora actual en la zona de El Salvador
        ZonedDateTime timestamp = ZonedDateTime.now(zonaElSalvador);

        // Crear un formateador de fecha y hora
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss:SSS");

        // Formatear la fecha y hora en el formato deseado
        String formattedTimestamp = timestamp.format(formatter);
		return formattedTimestamp;
	}

    public static Timestamp getCurrentTimeInElSalvador() {
        // Obtiene la fecha y hora actuales del sistema
        Calendar calendar = Calendar.getInstance();

        // Establece la zona horaria de El Salvador
        calendar.setTimeZone(TimeZone.getTimeZone("America/El_Salvador"));

        // Devuelve la fecha y hora ajustadas
        return new Timestamp(calendar.getTimeInMillis());
    }

}
