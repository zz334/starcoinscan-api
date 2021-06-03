package org.starcoin.scan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.starcoin.scan.bean.Transaction;
import org.starcoin.scan.service.TransactionService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping("get/{network}/{id}")
    public Transaction getTransaction(@PathVariable("network") String network, @PathVariable("id") String id) throws IOException {
        return transactionService.get(network,id);
    }

    @GetMapping("list/{network}/{page}")
    public List<Transaction> getRangeTransactions(@PathVariable("network") String network, @PathVariable("page") int page,
                                      @RequestParam(value = "count", required = false, defaultValue = "20") int count) throws Exception {
        return transactionService.getRange(network, page, count);
    }

    @GetMapping("address/{network}/{address}/{page}")
    public List<Transaction> getRangeByAddress(@PathVariable("network") String network,@PathVariable("address") String address, @PathVariable("page") int page,
                                               @RequestParam(value = "count", required = false, defaultValue = "20") int count) throws IOException {
        return transactionService.getRangeByAddress(network,address,page,count);
    }

    @GetMapping("block_hash/{network}/{block_hash}")
    public List<Transaction> getByBlockHash(@PathVariable("network") String network,@PathVariable("block_hash") String  blockHash) throws IOException {
        return  transactionService.getByBlockHash(network,blockHash);
    }

    @GetMapping("block_height/{network}/{block_height}")
    public List<Transaction> getByBlockHeight(@PathVariable("network") String network,@PathVariable("block_height") int blockHeight) throws IOException {
        return transactionService.getByBlockHeight(network,blockHeight);
    }
}