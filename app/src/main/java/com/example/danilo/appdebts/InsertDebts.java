package com.example.danilo.appdebts;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import com.example.danilo.appdebts.classes.Category;
import com.example.danilo.appdebts.classes.Debts;
import com.example.danilo.appdebts.classes.MainWindow;
import com.example.danilo.appdebts.dao.CategoryDAO;
import com.example.danilo.appdebts.dao.DebtsDAO;
import com.example.danilo.appdebts.database.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class InsertDebts extends AppCompatActivity {
    EditText mEditTextDataPay;
    Spinner mSpinnerCategory;
    final Calendar myCalendar = Calendar.getInstance();
    EditText mEditTextValue;
    EditText mEditTextDescription;
    Switch mSwitchPayed;
    EditText mTextViewPayment;

    //inserção no banco de dados
    CategoryDAO mCategoryDAO;
    DebtsDAO mDebtsDAO;
    private SQLiteDatabase mConection;
    private DatabaseHelper mDataHelper;

    //int mCategorySelected = 0;
    String newCategory = "";
    private boolean mFlag;
    private Debts mDebt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_debts);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true); //Ativar o botão
        getSupportActionBar().setTitle(R.string.title_activity_insert_debts);

        mEditTextValue = findViewById(R.id.editText);
        mEditTextDescription = findViewById(R.id.editTextDescription);
        mSwitchPayed = findViewById(R.id.switchPay);

        mSpinnerCategory = findViewById(R.id.spinnerCategories);

        mEditTextDataPay = findViewById(R.id.editTextDate);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        mEditTextDataPay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(InsertDebts.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        //botão de adicionar categoria
        FloatingActionButton fab = findViewById(R.id.floatingActionButtonAddCategory);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(InsertDebts.this);
                builder.setTitle(R.string.newCategoryTitle);

// Set up the input
                final EditText input = new EditText(InsertDebts.this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

// Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Category cat = mCategoryDAO.insert(new Category(input.getText().toString()));

                        newCategory = cat.getMtipo();
                        updateSpinnerCategory();
                        //m_Text = input.getText().toString();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        createConnection();
        updateSpinnerCategory();


    }

    //atualiza os itens da categoria
    public void updateSpinnerCategory() {
        List<Category> categories = mCategoryDAO.listCategories();
        mSpinnerCategory.setAdapter(null);

        final List<String> list = new ArrayList<String>();
        for(int i=0;i<categories.size();i++){
            Category cat = categories.get(i);
            list.add(cat.getMtipo());
            //if(cat.getType()==newCategory) mCategorySelected = i;
        }

        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerCategory.setAdapter(adp1);
        mSpinnerCategory.setSelection(adp1.getPosition(newCategory));
    }

    mTextViewPayment = findViewById(R.id.textViewPaymentorPay);
    mTextViewPay = findViewById(R.id.textViewPay);
    mEditTextPay = findViewById(R.id.editTextDatePay);
    checkParameters();
    updateUI();

        mEditTextPay.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            // TODO Auto-generated method stub
                new DatePickerDialog(InsertDebts. this, date, myCalendar
                    .get(Calendar. YEAR), myCalendar.get(Calendar. MONTH),
                    myCalendar.get(Calendar. DAY_OF_MONTH)).show();
        }
    });

    private void updateUI(){
        if(mFlag){ //inserção
            mTextViewPayment.setVisibility(View.GONE);
            mEditTextDataPay.setVisibility(View.GONE);
        }else{
            mTextViewPayment.setVisibility(View.VISIBLE);
            mEditTextDataPay.setVisibility(View.VISIBLE);
            mSwitchPayed.setVisibility(View.GONE);
            getSupportActionBar().setTitle("Change Debt");
            mTextViewPayment.setText("Payment");
        }
    }

    private void checkParameters(){
        Bundle bundle = getIntent().getExtras();
        if((bundle!=null) && bundle.containsKey( "DEBT")){
            mDebt = (Debts) bundle.getSerializable( "DEBT");
            mEditTextDescription.setText(mDebt.getDescricao());
            mEditTextDataPay.setText(mDebt.getPaymentDate());
            mEditTextDataPay.setText(mDebt.getPaymentDate());
            mEditTextValue.setText(String. valueOf(mDebt.getValor()));
            mSpinnerCategory.setSelection( mAdapterCategory.getPosition( mDebt.getCategory().getType()));
            mFlag = false;
        }
    }

    //criar conexão
    private void createConnection() {
        try {
            mDataHelper = new DatabaseHelper(this);
            mConection = mDataHelper.getWritableDatabase();
            mDebtsDAO = new DebtsDAO(mConection);
            mCategoryDAO = new CategoryDAO(mConection);
            //Snackbar.make(mLayout, R.string.sucess_conection, Snackbar.LENGTH_LONG).show();
        } catch (SQLException e) {
            //Snackbar.make(mLayout, e.toString(), Snackbar. LENGTH_LONG).show();
        }
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mEditTextDataPay.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_debts,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //verifica os dados adicionados pelo formulario
    private Debts checkData(){
        Debts debt = null;
        String msg = "";
        if(mEditTextDescription.getText().toString().isEmpty())
            msg = "*Informe a descrição do débito.\n";

        if(mEditTextValue.getText().toString().isEmpty())
            msg += "*Informe o valor do débito.\n";
        else if(Float.parseFloat(mEditTextValue.getText().toString())<=0)
            msg += "*Informe um valor válido (>0) para o débito.\n";

        if(mEditTextDataPay.getText().toString().isEmpty())
            msg += "*Informe a data do débito.\n";

        if(!msg.isEmpty())
            createAlertDialog(msg);
        else{
            //criar uma instância do débito
            debt = new Debts();
            debt.setCod_cat(mCategoryDAO.getCategory(mSpinnerCategory.getSelectedItem().toString()));
            debt.setPaymentDate(mEditTextDataPay.getText().toString());
            debt.setDescricao(mEditTextDescription.getText().toString());
            debt.setValor(Float.parseFloat(mEditTextValue.getText().toString()));
            if(mSwitchPayed.isChecked()){
                debt.setPaymentDate(debt.getPaymentDate());
            }else{
                debt.setPaymentDate("");
            }
        }
        return debt;
    }

    private void createAlertDialog(String msg) {
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle("Erro");
        //define a mensagem
        builder.setMessage(msg);
        builder.setCancelable(true);
        builder.create();
        //Exibe
        builder.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home: //ID do seu botão (gerado automaticamente pelo android, usando como  está, deve funcionar
                startActivity( new Intent(this, MainWindow.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            case R.id.okmenu:
                Debts debt = checkData();
                if(debt!=null){
                    mDebtsDAO.insert(debt);
                    startActivity( new Intent(this, MainWindow.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
                    finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem
                }
                //Log.d("Item Menu","Menu: "+R.string.okMenu);
                break;
            default:break;

        }
        return true;
    }
}
