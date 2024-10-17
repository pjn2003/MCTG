package at.technikum_wien.controller;

import at.technikum_wien.dummydata.DummyPackages;
import at.technikum_wien.httpserver.http.ContentType;
import at.technikum_wien.httpserver.http.HttpStatus;
import at.technikum_wien.httpserver.server.Response;



public class PackagesController extends Controller{

    private DummyPackages dummyPackages;
    public PackagesController() {
        this.dummyPackages = new DummyPackages();
    }


    public Response getPacks()
    {
        String result = this.dummyPackages.printPackages();
        return new Response(
                HttpStatus.OK,
                ContentType.JSON,
                "{ \"message\" : \"These packs are currently available:\"\n%s }".formatted(result)
        );

    }

    public Response createPack(String packName, Integer[] pack)
    {
        //System.out.println(Arrays.toString(pack));
        this.dummyPackages.addPackage(packName, pack);
        return new Response(
                HttpStatus.CREATED,
                ContentType.JSON,
                "{ \"message\" : \"Pack created successfully.\" }"
        );
    }




}
