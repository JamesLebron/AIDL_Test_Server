package com.james.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * 1.声明穿成员变量BookManager
 * 2.实现BookManager里面的方法
 * 3.在onBind() 里面返回BookManager
 */
public class AIDLService extends Service {
    private final String TAG = "James";
    private List<Book> mBooks = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        Book book = new Book();
        book.setName("装逼宝典");
        book.setPrice(100);
        mBooks.add(book);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBookManager;
    }

    private final BookManager.Stub mBookManager = new BookManager.Stub() {
        @Override
        public List<Book> getBooks() throws RemoteException {
            synchronized (this) {
                if (mBooks != null) {
                    return mBooks;
                }
                return new ArrayList<Book>();
            }
        }

        @Override
        public Book addBookIn(Book book) throws RemoteException {
            synchronized (this) {
                if (mBooks == null) {
                    mBooks = new ArrayList<>();
                }
                if (book == null) {
                    Log.e(TAG, "Book is null in In");
                    book = new Book();
                }
                //尝试修改book的参数，主要是为了观察其到客户端的反馈
                book.setPrice(2333);
                if (!mBooks.contains(book)) {
                    mBooks.add(book);
                }
                //打印mBooks列表，观察客户端传过来的值
                Log.e(TAG, "invoking addBooks() method , now the list is : " + mBooks.toString());
                return book;
            }
        }

        @Override
        public Book addBookOut(Book book) throws RemoteException {
            synchronized (this) {
                if (mBooks == null) {
                    mBooks = new ArrayList<>();
                }
                if (book == null) {
                    Log.e(TAG, "Book is null in Out");
                    book = new Book();
                }
                book.setPrice(2333);
                if (!mBooks.contains(book)) {
                    mBooks.add(book);
                }
                Log.e(TAG, "invoking addBooks() method , now the list is : " + mBooks.toString());
                return book;
            }
        }

        @Override
        public Book addBookInout(Book book) throws RemoteException {
            synchronized (this) {
                if (mBooks == null) {
                    mBooks = new ArrayList<>();
                }
                if (book == null) {
                    Log.e(TAG, "Book is null in Inout");
                    book = new Book();
                }
                book.setPrice(2333);
                if (!mBooks.contains(book)) {
                    mBooks.add(book);
                }
                Log.e(TAG, "invoking addBooks() method , now the list is : " + mBooks.toString());
                return book;
            }
        }
    };
}
