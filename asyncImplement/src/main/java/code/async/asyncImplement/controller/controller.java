package code.async.asyncImplement.controller;

import code.async.asyncImplement.entity.combine;
import code.async.asyncImplement.service.service;
import code.async.asyncImplement.entity.db;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api")
public class controller {
    @Autowired
    private service userService;
    @PostMapping("/post")
    public ResponseEntity<Integer> save(@RequestParam String name, @RequestParam String email) {
        return new ResponseEntity<>(userService.save(name, email), HttpStatus.OK);
    }
    @GetMapping("/get")
    public CompletableFuture<ResponseEntity<db>> get(@RequestParam Integer id) throws InterruptedException {
        return userService.get(id).thenApply(ResponseEntity::ok);
        //userService.get(id).thenApply( value -> ResponseEntity.ok(value) );
    }
    //or
    //    @GetMapping("/get")
    //    public CompletableFuture<db> get(@RequestParam Integer id) throws InterruptedException {
    //        return userService.get(id);
    //    }
    @GetMapping("/getAll")
    public CompletableFuture<ResponseEntity<List<db>>> getAll() throws InterruptedException {
        return userService.getAll().thenApply(ResponseEntity::ok);
        //userService.getAll().thenApply( value -> ResponseEntity.ok(value) );
    }
    @GetMapping("/combine")
    public CompletableFuture<ResponseEntity<combine.data>> data(@RequestParam Integer a, @RequestParam Integer b, @RequestParam String value) throws InterruptedException {
        CompletableFuture<combine.data1> vdata1 = userService.data1(a);
        IO.println("Data-1 API");
        CompletableFuture<combine.data2> vdata2 = userService.data2(b);
        IO.println("Data-2 API");
        CompletableFuture<combine.data3> vdata3 = userService.data3(value);
        IO.println("Data-3 API");
        //thenApply    -> transform result
        //thenCompose  -> call another async method after first result
        //thenCombine  -> combine two independent futures
        //allOf        -> wait for many independent futures
        //First async call.
        //CompletableFuture<User> userFuture = userClient.getUser(userId);
        //After userFuture completes, start another async call.
        //CompletableFuture<List<Order>> ordersFuture = userFuture.thenCompose(user -> {
        // This returns another CompletableFuture.
        //  return orderClient.getOrdersByUserId(user.id());
        //});
        return CompletableFuture.allOf(vdata1, vdata2, vdata3).thenApply(
                v -> {
                    combine.data1 data1 = vdata1.join();
                    combine.data2 data2 = vdata2.join();
                    combine.data3 data3 = vdata3.join();
                    combine.data data = new combine.data(
                            data1.a() + data2.b(),
                            data3.value()
                    );
                    return ResponseEntity.ok(data);
                })
                .exceptionally(e-> {
                    combine.data data = new combine.data(
                            Integer.MAX_VALUE,
                            "Error"
                    );
                    return ResponseEntity.internalServerError().body(data);
                });
    }
    @GetMapping("cpuExe")
    public CompletableFuture<ResponseEntity<?>> exe(@RequestParam int from, @RequestParam int to) {
        long time1 = System.currentTimeMillis();
        return userService.cpuExe(from, to).thenApply( sum -> {
            Map<String, Object> map = new HashMap<>();
            map.put("from", from);
            map.put("to", to);
            map.put("result", sum);
            map.put("time", System.currentTimeMillis() - time1);
            return ResponseEntity.ok(map);
        });
    }
}
