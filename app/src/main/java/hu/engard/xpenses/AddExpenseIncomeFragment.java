package hu.engard.xpenses;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import hu.engard.expenses.generated.tables.pojos.Account;
import hu.engard.xpenses.fragments.AccountChooserFragment;
import hu.engard.xpenses.fragments.TextEntryDialogFragment;
import layout.NumericKeypadFragment;

/**
 * Created by fery on 1/8/16.
 */
public class AddExpenseIncomeFragment extends Fragment implements NumericKeypadFragment.Listener, AccountChooserFragment.Listener, TextEntryDialogFragment.Listener {
  private static final String ARG_ISEXPENSE = "kind";
  private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";
  private static final String TAG = "AddEIF";

  private ViewGroup container;
  private TextView amountEntry;
  private TextView accountEdit;
  private TextView dateEntry;
  private TextView commentEntry;

  private Account selectedAccount;

  /**
   * Returns a new instance of this fragment for the given section
   * number.
   */
  public static AddExpenseIncomeFragment newInstance(boolean isExpense) {
    AddExpenseIncomeFragment fragment = new AddExpenseIncomeFragment();
    Bundle args = new Bundle();
    args.putBoolean(ARG_ISEXPENSE, isExpense);
    fragment.setArguments(args);
    return fragment;
  }

  public AddExpenseIncomeFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    this.container = container;
    View rootView = inflater.inflate(R.layout.fragment_add_transaction, container, false);
//    TextView textView = (TextView) rootView.findViewById(R.id.section_label);
//    textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
    Log.i(TAG, "onCreateView");
    amountEntry = ((TextView) rootView.findViewById(R.id.amount_entry));
    amountEntry.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        onAmountEntryClick(view);
      }
    });

    accountEdit = ((TextView) rootView.findViewById(R.id.account_entry));
    rootView.findViewById(R.id.account_area).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        onAccountEntryClick(view);
      }
    });

    dateEntry = ((TextView) rootView.findViewById(R.id.date_entry));
    dateEntry.setText(new SimpleDateFormat(DATE_FORMAT).format(new Date()));
    ImageButton datePrev = ((ImageButton) rootView.findViewById(R.id.date_prev));
    datePrev.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        try {
          Date date = new SimpleDateFormat(DATE_FORMAT).parse(dateEntry.getText().toString());
          dateEntry.setText(new SimpleDateFormat(DATE_FORMAT).format(new Date(date.getTime() - 86400 * 1000)));
        } catch (ParseException e) {
          Log.i(TAG, "invalid date " + dateEntry.getText());
          dateEntry.setText(new SimpleDateFormat(DATE_FORMAT).format(new Date()));
        }
      }
    });
    ImageButton dateNext = ((ImageButton) rootView.findViewById(R.id.date_next));
    dateNext.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        try {
          Date date = new SimpleDateFormat(DATE_FORMAT).parse(dateEntry.getText().toString());
          dateEntry.setText(new SimpleDateFormat(DATE_FORMAT).format(new Date(date.getTime() + 86400 * 1000)));
        } catch (ParseException e) {
          Log.i(TAG, "invalid date " + dateEntry.getText());
          dateEntry.setText(new SimpleDateFormat(DATE_FORMAT).format(new Date()));
        }
      }
    });
    rootView.findViewById(R.id.date_entry).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        dateEntry.setText(new SimpleDateFormat(DATE_FORMAT).format(new Date()));
      }
    });

    View commentArea = rootView.findViewById(R.id.comment_area);
    commentArea.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onCommentEntryClick(v);
      }
    });
    commentEntry=(TextView) rootView.findViewById(R.id.comment_entry);

    return rootView;
  }

  private void onCommentEntryClick(View view) {
    Log.i(TAG, "onCommentEntryClick");
    FragmentManager fm = getFragmentManager();
    TextEntryDialogFragment fr = TextEntryDialogFragment.newInstance("Enter comment:", "", null, this);
    fr.show(fm, "textentry");
  }

  private void onAccountEntryClick(View view) {
    Log.i(TAG, "onAccountEntryClick");
    showAccountChooser(accountEdit.getText());
  }

  private void showAccountChooser(CharSequence account) {
    FragmentManager fm = getFragmentManager();
    AccountChooserFragment fr = AccountChooserFragment.newInstance(account, null, this);
    fr.show(fm, "numpad");
  }

  public void onAmountEntryClick(View view) {
    Log.i(TAG, "onAmountEntryClick");
    showNumericKeypad("", null);
  }

  private void showNumericKeypad(String number, Serializable param) {
    FragmentManager fm = getFragmentManager();
    NumericKeypadFragment numericKeypad = NumericKeypadFragment.newInstance(number, param, this);
    numericKeypad.show(fm, "numpad");
  }

  @Override
  public void onNumericTextChanged(String text, Serializable param) {
    Log.i(TAG, "onNumericTextChanged: " + text);
    amountEntry.setText(text);
  }

  @Override
  public void onNumericDone(Serializable param) {
    // nothing
  }

  @Override
  public void onNumericCanceled(Serializable param) {
    // nothing
  }

  @Override
  public void onAccountSelected(Account account, Serializable param) {
    selectedAccount = account;
    accountEdit.setText(account.getName());
  }

  @Override
  public void onAccountSelectCanceled(Serializable param) {
    // nothing
  }

  @Override
  public void onTextEntered(CharSequence text, Serializable param) {
    commentEntry.setText(text);
  }

  @Override
  public void onTextEntryCanceled(Serializable param) {
    // nothing
  }
}
