package com.xiong.myprojectbackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiong.myprojectbackend.entity.RestBean;
import com.xiong.myprojectbackend.entity.po.DbBook;
import com.xiong.myprojectbackend.entity.vo.response.PaginationVO;
import com.xiong.myprojectbackend.entity.vo.resquest.SelectPageVO;
import com.xiong.myprojectbackend.service.DbBookService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author 10371
 * @Description
 * @create 2023/10/19 00:49:04
 */
@RestController
@RequestMapping("/api/book")
public class BookController {

    @Resource
    DbBookService dbBookService;

    @RequestMapping("/getPageList")
    public RestBean<Object> getPageList(SelectPageVO selectPageVO){
        List<DbBook> dbBooks = dbBookService.getPageList(selectPageVO);
        if (dbBooks.size() > 0)return RestBean.success(dbBooks);
        return RestBean.failure(400,"查询失败");
    }

    @RequestMapping("/getPagination")
    public RestBean<Object> getPagination(@RequestParam("pageSize") Integer pageSize){
        PaginationVO pagination = dbBookService.getPagination(pageSize);
        if (pagination.getPageCount() > 0)return RestBean.success(pagination);
        return RestBean.failure(400,"查询失败");
    }


    @RequestMapping("/getBookById")
    public RestBean<Object> getBookById(@RequestParam("id") int id){
        if (id > 0){
            DbBook dbBooks = dbBookService.getById(id);
            if (dbBooks.getBookId() > 0)return RestBean.success(dbBooks);
        }
        return RestBean.failure(400,"查询失败");
    }

    @RequestMapping("/getBookByIds")
    public RestBean<Object> getBookByIds(@RequestParam("ids") String ids){
        if (ids.length() > 0){
            List<DbBook> dbBooks = dbBookService.getBookByIds(ids);
            if (dbBooks.size() > 0)return RestBean.success(dbBooks);
        }
        return RestBean.failure(400,"查询失败");
    }

    @RequestMapping("/getBookByName")
    public RestBean<Object> getBookByName(@RequestParam("bookName") String bookName){
        List<DbBook> dbBooks = dbBookService.getBookByName(bookName);
        if (dbBooks.size() > 0)return RestBean.success(dbBooks);
        return RestBean.failure(400,"查询失败");
    }

    @RequestMapping("/getBookNameList")
    public RestBean<Object> getBookByName(){
        List<DbBook> dbBooks = dbBookService.list(new QueryWrapper<DbBook>().select("book_name"));
        if (dbBooks.size() > 0)return RestBean.success(dbBooks);
        return RestBean.failure(400,"查询失败");
    }

    @RequestMapping("/getTagList")
    public RestBean<Object> getTagList(@RequestParam("tagName") String tagName){
        List<DbBook> dbBooks = dbBookService.getTagList("tagName");
        if (dbBooks.size() > 0)return RestBean.success(dbBooks);
        return RestBean.failure(400,"查询失败");
    }

    @RequestMapping("/save")
    public RestBean<Void> save(@RequestBody DbBook dbBook){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dbBook.setAddTime(dateFormat.format(date));
        dbBook.setSales(0);
        return dbBookService.save(dbBook) ? RestBean.success() : RestBean.failure(400,"保存失败");
    }

    @RequestMapping("/removeById")
    public RestBean<Void> removeById(@RequestParam("id") int id){
        return dbBookService.removeById(id) ? RestBean.success() : RestBean.failure(400,"删除失败");
    }

    @RequestMapping("/update")
    public RestBean<Void> update(@RequestBody DbBook dbBook){
        return dbBookService.updateById(dbBook) ? RestBean.success() : RestBean.failure(400,"更新失败");
    }

    @RequestMapping("/updateById")
    public RestBean<Void> updateById(@RequestBody int id, String column, String setValue){
        return dbBookService.updateById(id, column, setValue) ? RestBean.success() : RestBean.failure(400,"更新失败");
    }

    @RequestMapping("/updateByIdInt")
    public RestBean<Void> updateByIdInt(@RequestParam("id") int id,@RequestParam("column") String column,@RequestParam("value") int setValue){
        return dbBookService.updateById(id, column, setValue) ? RestBean.success() : RestBean.failure(400,"更新失败");
    }

}
