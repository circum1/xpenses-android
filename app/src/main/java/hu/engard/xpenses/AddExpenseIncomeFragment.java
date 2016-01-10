package hu.engard.xpenses;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import layout.NumericKeypadFragment;

/**
 * Created by fery on 1/8/16.
 */
public class AddExpenseIncomeFragment extends Fragment {
  private static final String ARG_ISEXPENSE = "kind";

  private ViewGroup container;

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
    Log.i("AddExpenseIncomeFragment", "onCreateView");
    rootView.findViewById(R.id.amount_entry).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Log.i("AddExpenseIncomeFragment", "onClick");
        onAmountEntryClick(view);
      }
    });
    return rootView;
  }

  public void onAmountEntryClick(View view) {
    Log.i("AddExpenseIncomeFragment", "onAmountEntryClick");
    showNumericKeypad();
  }

  private void showNumericKeypad() {
    FragmentManager fm = getFragmentManager();
    NumericKeypadFragment numericKeypad = new NumericKeypadFragment();
    numericKeypad.show(fm, "tag");
  }

}
