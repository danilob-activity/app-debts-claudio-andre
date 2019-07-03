package com.example.danilo.appdebts;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import com.example.danilo.appdebts.classes.Category;
import com.example.danilo.appdebts.classes.Debts;
import com.example.danilo.appdebts.dao.CategoryDAO;
import com.example.danilo.appdebts.dao.DebtsDAO;
import com.example.danilo.appdebts.database.DatabaseHelper;

public class TelaInicial extends AppCompatActivity {

    private SQLiteDatabase mConection;
    private DatabaseHelper mDataHelper;
    private ConstraintLayout mLayout;
    private CategoryDAO mCategoryDAO;
    private DebtsDAO mDebtsDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);

        mLayout = findViewById(R.id.Layout);

        createConnection();
        Category cat = new Category("Tia do lanche");
        CategoryDAO catDao = new CategoryDAO(mConection);
        catDao.insert(cat);
        catDao.listCategories();
        mConection.close();
        cat.setMtipo("Energia");
        catDao.alter(cat);
        //catDao.remove(7);

    }
    private void createConnection() {
        try {
            mDataHelper = new DatabaseHelper(this);
            mConection = mDataHelper.getWritableDatabase();
            Snackbar.make(mLayout, R.string.sucess_conection, Snackbar.LENGTH_LONG).show();
        } catch (SQLException e) {
            Snackbar.make(mLayout, e.toString(), Snackbar.LENGTH_LONG).show();
        }
    }

    public void  populateDatabase(){
        createConnection();
        Category cat1 = new Category("Casa");
        cat1 = mCategoryDAO.insert(cat1);

        Category cat2 = new Category("Quitanda");
        cat2 = mCategoryDAO.insert(cat2);

        Category cat3 = new Category("Farmacia");
        cat3 = mCategoryDAO.insert(cat3);

        Debts debt1 = new Debts(cat1, (float)79.81, "produtos de limpeza", "20/08/2019", "30/08/2019");
        debt1 = mDebtsDAO.insert(debt1);


        Debts debt2 = new Debts(cat2, (float) 5.0,"Coxinha" , "5/07/2019", "3/07/2019");
        debt2 = mDebtsDAO.insert(debt2);

        Debts debt3 = new Debts(cat3, (float) 5.0,"Vita C" , "3/07/2019", "25/07/2019");
        debt3 = mDebtsDAO.insert(debt3);
    }
}
