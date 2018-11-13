package com.group3.ca3.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.group3.ca3.rest.http.*;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("holidays")
public class HolidayResource
{

    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private static HolidayCountry[] countries = new HolidayCountry[]{
            new HolidayCountry("Andorra", "AD"),
            new HolidayCountry("Argentina", "AR"),
            new HolidayCountry("Austria", "AT"),
            new HolidayCountry("Australia", "AU"),
            new HolidayCountry("Åland Islands", "AX"),
            new HolidayCountry("Barbados", "BB"),
            new HolidayCountry("Belgium", "BE"),
            new HolidayCountry("Bulgaria", "BG"),
            new HolidayCountry("Bolivia", "BO"),
            new HolidayCountry("Brazil", "BR"),
            new HolidayCountry("Bahamas, The", "BS"),
            new HolidayCountry("Botswana", "BW"),
            new HolidayCountry("Belarus", "BY"),
            new HolidayCountry("Belize", "BZ"),
            new HolidayCountry("Canada", "CA"),
            new HolidayCountry("Switzerland", "CH"),
            new HolidayCountry("Chile", "CL"),
            new HolidayCountry("China", "CN"),
            new HolidayCountry("Colombia", "CO"),
            new HolidayCountry("Costa Rica", "CR"),
            new HolidayCountry("Cuba", "CU"),
            new HolidayCountry("Cyprus", "CY"),
            new HolidayCountry("Czech Republic", "CZ"),
            new HolidayCountry("Germany", "DE"),
            new HolidayCountry("Denmark", "DK"),
            new HolidayCountry("Dominican Republic", "DO"),
            new HolidayCountry("Ecuador", "EC"),
            new HolidayCountry("Estonia", "EE"),
            new HolidayCountry("Egypt", "EG"),
            new HolidayCountry("Spain", "ES"),
            new HolidayCountry("Finland", "FI"),
            new HolidayCountry("Faroe Islands", "FO"),
            new HolidayCountry("France", "FR"),
            new HolidayCountry("Gabon", "GA"),
            new HolidayCountry("United Kingdom", "GB"),
            new HolidayCountry("Grenada", "GD"),
            new HolidayCountry("Greenland", "GL"),
            new HolidayCountry("Greece", "GR"),
            new HolidayCountry("Guatemala", "GT"),
            new HolidayCountry("Guyana", "GY"),
            new HolidayCountry("Honduras", "HN"),
            new HolidayCountry("Croatia", "HR"),
            new HolidayCountry("Haiti", "HT"),
            new HolidayCountry("Hungary", "HU"),
            new HolidayCountry("Ireland", "IE"),
            new HolidayCountry("Man, Isle of", "IM"),
            new HolidayCountry("Iceland", "IS"),
            new HolidayCountry("Italy", "IT"),
            new HolidayCountry("Jersey", "JE"),
            new HolidayCountry("Jamaica", "JM"),
            new HolidayCountry("Liechtenstein", "LI"),
            new HolidayCountry("Lesotho", "LS"),
            new HolidayCountry("Lithuania", "LT"),
            new HolidayCountry("Luxembourg", "LU"),
            new HolidayCountry("Latvia", "LV"),
            new HolidayCountry("Morocco", "MA"),
            new HolidayCountry("Monaco", "MC"),
            new HolidayCountry("Moldova", "MD"),
            new HolidayCountry("Madagascar", "MG"),
            new HolidayCountry("Macedonia, Former Yugoslav Republic of", "MK"),
            new HolidayCountry("Malta", "MT"),
            new HolidayCountry("Mexico", "MX"),
            new HolidayCountry("Mozambique", "MZ"),
            new HolidayCountry("Namibia", "NA"),
            new HolidayCountry("Nicaragua", "NI"),
            new HolidayCountry("Netherlands", "NL"),
            new HolidayCountry("Norway", "NO"),
            new HolidayCountry("New Zealand", "NZ"),
            new HolidayCountry("Panama", "PA"),
            new HolidayCountry("Peru", "PE"),
            new HolidayCountry("Poland", "PL"),
            new HolidayCountry("Puerto Rico", "PR"),
            new HolidayCountry("Portugal", "PT"),
            new HolidayCountry("Paraguay", "PY"),
            new HolidayCountry("Romania", "RO"),
            new HolidayCountry("Serbia", "RS"),
            new HolidayCountry("Russia", "RU"),
            new HolidayCountry("Sweden", "SE"),
            new HolidayCountry("Slovenia", "SI"),
            new HolidayCountry("Svalbard", "SJ"),
            new HolidayCountry("Slovakia", "SK"),
            new HolidayCountry("San Marino", "SM"),
            new HolidayCountry("Suriname", "SR"),
            new HolidayCountry("El Salvador", "SV"),
            new HolidayCountry("Tunisia", "TN"),
            new HolidayCountry("Turkey", "TR"),
            new HolidayCountry("Ukraine", "UA"),
            new HolidayCountry("United States", "US"),
            new HolidayCountry("Uruguay", "UY"),
            new HolidayCountry("Vatican City", "VA"),
            new HolidayCountry("Bolivarian Republic of Venezuela", "VE"),
            new HolidayCountry("South Africa", "ZA")
    };

    @GET
    @Path("countries")
    @Produces(APPLICATION_JSON)
    public Response getCountries()
    {
        return Response.ok().entity(gson.toJson(countries)).build();
    }

    @GET
    @Path("{countryCode}")
    @Produces(APPLICATION_JSON)
    public Response getHolidays(@PathParam("countryCode") String countryCode) throws HttpException
    {
        HttpClient httpClient = new HttpClient();
        HttpRequest httpRequest = HttpRequestBuilder.getInstance()
                                                    .get()
                                                    .url("http://date.nager.at/api/v1/get/%s/2018", countryCode)
                                                    .acceptJSON()
                                                    .build();
        HttpResponse httpResponse = httpClient.perform(httpRequest);
        return Response.status(httpResponse.responseCode).entity(httpResponse.contents).build();
    }

    private static class HolidayCountry
    {
        public final String name;
        public final String code;

        public HolidayCountry(String name, String code)
        {
            this.name = name;
            this.code = code;
        }
    }
}
