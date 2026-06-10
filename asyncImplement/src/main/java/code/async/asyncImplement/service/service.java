package code.async.asyncImplement.service;

import code.async.asyncImplement.entity.combine;
import code.async.asyncImplement.repo.repo;
import code.async.asyncImplement.entity.db;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.lang.Thread.sleep;

@Service
public class service {
    @Autowired
    private repo Repo;
    public Integer save(String name, String email) {
        db userTable = new db();
        userTable.setName(name);
        userTable.setEmail(email);
        return Repo.save(userTable).getId();
    }
    @Async("apiExecutor")
    public CompletableFuture<db> get(Integer id) throws InterruptedException {
        IO.println("API-1 get running on " + Thread.currentThread().getName());
        sleep(500);
        return CompletableFuture.completedFuture(Repo.findById(id).orElse(null));
    }
    @Async("apiExecutor")
    public CompletableFuture<List<db>> getAll() throws InterruptedException {
        IO.println("API-2 getAll running on " + Thread.currentThread().getName());
        sleep(500);
        return CompletableFuture.completedFuture(Repo.findAll());
    }
    @Async("apiExecutor")
    public CompletableFuture<combine.data1> data1(Integer a) throws InterruptedException {
        IO.println("Data-1 API is running on " + Thread.currentThread().getName());
        sleep(1000);
        combine.data1 value = new combine.data1(a);
        return CompletableFuture.completedFuture(value);
    }
    @Async("apiExecutor")
    public CompletableFuture<combine.data2> data2(Integer b) throws InterruptedException {
        IO.println("Data-2 API is running on " + Thread.currentThread().getName());
        sleep(1000);
        combine.data2 value = new combine.data2(b);
        return CompletableFuture.completedFuture(value);
    }
    @Async("apiExecutor")
    public CompletableFuture<combine.data3> data3(String value) throws InterruptedException {
        IO.println("Data-3 API is running on " + Thread.currentThread().getName());
        sleep(1000);
        combine.data3 value1 = new combine.data3(value);
        return CompletableFuture.completedFuture(value1);
    }
}
