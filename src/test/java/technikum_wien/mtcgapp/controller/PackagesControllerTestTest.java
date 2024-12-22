package technikum_wien.mtcgapp.controller;

import technikum_wien.httpserver.http.ContentType;
import technikum_wien.httpserver.http.HttpStatus;
import technikum_wien.httpserver.server.Response;
import technikum_wien.mtcgapp.dummydata.DummyPackages;


public class PackagesControllerTestTest extends ControllerTest {

    private DummyPackages dummyPackages;
    public PackagesControllerTestTest() {
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
