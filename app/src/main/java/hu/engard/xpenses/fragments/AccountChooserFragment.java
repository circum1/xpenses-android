package hu.engard.xpenses.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

import hu.engard.expenses.generated.tables.pojos.Account;
import hu.engard.xpenses.dao.AccountDao;

/**
 * Activities that contain this fragment must implement the
 * {@link Listener} interface
 * to handle interaction events.
 * Use the {@link AccountChooserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountChooserFragment extends DialogFragment {
  // TODO: Rename parameter arguments, choose names that match
  private static final String ARG_TEXT = "text";
  private static final String ARG_PARAM = "param";

  // text to edit
  private String text;
  // opaque parameter received on construction which is passed to the
  // callback methods
  private Serializable param;

  // used only if the listener is not a fragment, but an activity
  private Listener mListener;

  private Listener listener() {
    Listener frag = (Listener) getTargetFragment();
    if (frag != null) {
      return frag;
    }
    if (mListener != null) {
      return mListener;
    }
    throw new RuntimeException("No Listener passed to AccountChooserFragment");
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param param1 Parameter 1.
   * @param param2 Parameter 2.
   * @return A new instance of fragment AccountChooserFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static AccountChooserFragment newInstance(CharSequence text, Serializable param, android.app.Fragment listener) {
    AccountChooserFragment fragment = new AccountChooserFragment();
    Bundle args = new Bundle();
    args.putCharSequence(ARG_TEXT, text);
    args.putSerializable(ARG_PARAM, param);
    fragment.setArguments(args);
    fragment.setTargetFragment(listener, 0);
    return fragment;
  }

  public AccountChooserFragment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      text = getArguments().getString(ARG_TEXT);
      param = getArguments().getString(ARG_PARAM);
    }
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    final List<Account> accounts = AccountDao.instance().getAll();

    builder.setTitle("Pick an account")
        .setAdapter(new ArrayAdapter<Account>(getActivity(), android.R.layout.select_dialog_item, accounts) {
          @Override
          public View getView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getView(position, convertView, parent);
            view.setText(getItem(position).getName());
            return view;
          }
        }, new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int which) {
            Log.i("ACF", "which: "+which+", selected");
            if (listener() != null) {
              listener().onAccountSelected(accounts.get(which), param);
            }
          }
        });
    return builder.create();
  }

//  @Override
//  public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                           Bundle savedInstanceState) {
//    // Inflate the layout for this fragment
//    View view = inflater.inflate(R.layout.fragment_category_chooser, container, false);
//
//    return view;
//  }

//  public void onButtonPressed(Uri uri) {
//    if (listener() != null) {
////      mListener.onFragmentInteraction(uri);
//    }
//  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof Listener) {
      mListener = (Listener) context;
    } else {
      mListener = null;
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  /**
   * This interface must be implemented by activities that contain this
   * fragment to allow an interaction in this fragment to be communicated
   * to the activity and potentially other fragments contained in that
   * activity.
   * <p/>
   * See the Android Training lesson <a href=
   * "http://developer.android.com/training/basics/fragments/communicating.html"
   * >Communicating with Other Fragments</a> for more information.
   */
  public interface Listener {
    // TODO: Update argument type and name
    void onAccountSelected(Account account, Serializable param);

    void onAccountSelectCanceled(Serializable param);
  }

}
