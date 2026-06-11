package code.async.asyncImplement.service;

import code.async.asyncImplement.entity.combine;
import code.async.asyncImplement.repo.repo;
import code.async.asyncImplement.entity.db;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

import static java.lang.Thread.sleep;

@Service
public class service {
    @Autowired
    private repo Repo;
    private final ExecutorService executorService;
    public service(@Qualifier("cpuExecutor") ExecutorService executorService) {
        this.executorService = executorService;
    }
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
        sleep(500);
        combine.data1 value = new combine.data1(a);
        return CompletableFuture.completedFuture(value);
    }
    @Async("apiExecutor")
    public CompletableFuture<combine.data2> data2(Integer b) throws InterruptedException {
        IO.println("Data-2 API is running on " + Thread.currentThread().getName());
        sleep(5000);
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
    public Long sq(int from, int to) {
        long result = 0;
        for(int i=from;i<=to;i++) {
            result += (long) i * i;
        }
        return result;
    }
    public CompletableFuture<Long> cpuExe(int from, int to) {
        if(from >= to) {
            return CompletableFuture.failedFuture(new IllegalArgumentException("To value must larger then from"));
        }
        int cores = Runtime.getRuntime().availableProcessors();
        int range = to-from+1;
        int chunkSize = (int) Math.max(1, Math.ceil(range/(double)cores));
        List<CompletableFuture<Long>> futures = new ArrayList<>();
        for(int i = from; i <= to; i += chunkSize) {
            int chunkStart = i;
            int chunkEnd = Math.min(to, i+chunkSize-1);
            CompletableFuture<Long> future = CompletableFuture.supplyAsync(
                    () -> sq(chunkStart, chunkEnd), executorService );
            futures.add(future);
        }
        CompletableFuture<Void> allDone = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        return allDone.thenApply( ignore -> futures.stream()
                    .mapToLong(CompletableFuture::join)
                    .sum()
        );
    }
}
/*
CompletableFuture<Void> allDone =
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

return allDone.thenApply(ignored -> futures.stream()
        .mapToLong(CompletableFuture::join)
        .sum()
);
Step 1: What is inside futures?

Suppose we split work into 4 chunks.

List<CompletableFuture<Long>> futures = new ArrayList<>();

Contains:

future1 -> 100
future2 -> 200
future3 -> 300
future4 -> 400

Visual:

futures
 |
 +--> CompletableFuture<Long> -> 100
 +--> CompletableFuture<Long> -> 200
 +--> CompletableFuture<Long> -> 300
 +--> CompletableFuture<Long> -> 400
Step 2: What does allOf() do?
CompletableFuture.allOf(...)

waits for all futures to complete.

Example:

CompletableFuture<Void> allDone =
        CompletableFuture.allOf(future1, future2, future3, future4);

Visual:

future1 ----done----
future2 ----done----
future3 ----done----
future4 ----done----

          |
          V

      allDone

Important:

allDone does NOT contain results.

Its type is:

CompletableFuture<Void>

Notice:

Void

not

Long

because allOf() only tells you:

"Everything finished."

It does not give:

100
200
300
400
Step 3: Why toArray(new CompletableFuture[0])?

allOf() expects:

CompletableFuture<?>...

which means:

future1, future2, future3

But we have:

List<CompletableFuture<Long>>

So we convert list to array.

futures.toArray(new CompletableFuture[0])

Example:

List
[
 future1,
 future2,
 future3
]

becomes

[
 future1,
 future2,
 future3
]

array.

Then:

CompletableFuture.allOf(array)

works.

Step 4: What is thenApply()?
allDone.thenApply(...)

means:

When allDone finishes,
execute this code.

Visual:

future1 done
future2 done
future3 done

      |
      V

allDone completed

      |
      V

thenApply(...)
Step 5: What is ignored?
thenApply(ignored -> ...)

Remember:

allDone

is:

CompletableFuture<Void>

So it returns:

Void

which is basically:

null

You don't need it.

So we write:

ignored

meaning:

"I received a value but I don't care about it."

Could also write:

thenApply(v -> ...)

or

thenApply(x -> ...)

Same thing.

Step 6: What is futures.stream()?

Suppose:

futures =
[
 future1,
 future2,
 future3,
 future4
]

Stream means:

futures.stream()

start processing them one by one.

Step 7: What is CompletableFuture::join?

This is the most important line.

.mapToLong(CompletableFuture::join)

Same as:

.mapToLong(future -> future.join())

Suppose:

future1 -> 100
future2 -> 200
future3 -> 300
future4 -> 400

Then:

future1.join()

returns:

100

and

future2.join()

returns:

200

and so on.

Result:

100
200
300
400
Why is join() safe here?

Normally:

future.join()

waits if future isn't finished.

Example:

future.join();

could block.

BUT

Before this line we already did:

CompletableFuture.allOf(...)

and

allDone.thenApply(...)

So:

future1 finished
future2 finished
future3 finished
future4 finished

already happened.

Therefore:

join()

returns immediately.

No waiting.

Step 8: What is mapToLong()?

After joining:

100
200
300
400

Now stream contains longs.

.mapToLong(...)

creates:

LongStream

containing:

100
200
300
400
Step 9: What is .sum()?
.sum()

adds everything.

100 + 200 + 300 + 400

Result:

1000
Full execution flow

Suppose:

future1 = 100
future2 = 200
future3 = 300
future4 = 400
Step 1
CompletableFuture.allOf(...)

waits for all.

Step 2
allDone.thenApply(...)

starts.

Step 3
futures.stream()

gives:

future1
future2
future3
future4
Step 4
.mapToLong(CompletableFuture::join)

becomes:

100
200
300
400
Step 5
.sum()

becomes:

1000
Final return
CompletableFuture<Long>

containing:

1000
Simpler version

The code:

return allDone.thenApply(ignored ->
        futures.stream()
                .mapToLong(CompletableFuture::join)
                .sum()
);

is exactly equivalent to:

return allDone.thenApply(ignored -> {

    long total = 0;

    for (CompletableFuture<Long> future : futures) {
        total += future.join();
    }

    return total;
});

This version is usually easier to understand when learning.

One-line meaning
CompletableFuture.allOf(...)

➡ Wait for all tasks.

CompletableFuture::join

➡ Get each task's result.

sum()

➡ Add all results together.

So the whole block means:

"Wait until every parallel task finishes, then collect all results and add them together."
 */