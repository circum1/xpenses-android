package layout;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import java.io.Serializable;

import hu.engard.xpenses.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Listener} interface
 * to handle interaction events.
 * Use the {@link NumericKeypadFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NumericKeypadFragment extends DialogFragment {
  private static final String ARG_TEXT = "text";
  private static final String ARG_PARAM = "param";

  // text to edit
  private String text;
  // opaque parameter received on construction which is passed to the
  // callback methods
  private Serializable param;

  private Listener mListener;
  private Listener listener() {
    Listener frag = (Listener) getTargetFragment();
    if (frag!=null) {
      return frag;
    }
    if (mListener!=null) {
      return mListener;
    }
    throw new RuntimeException("No Listener passed to NumericKeypadFragment");
  }


  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param text initial text
   * @return A new instance of fragment NumericKeypadFragment.
   */
  public static NumericKeypadFragment newInstance(CharSequence text, Serializable param, Fragment listener) {
    NumericKeypadFragment fragment = new NumericKeypadFragment();
    Bundle args = new Bundle();
    args.putCharSequence(ARG_TEXT, text);
    args.putSerializable(ARG_PARAM, param);
    fragment.setArguments(args);
    fragment.setTargetFragment(listener, 0);
    return fragment;
  }

  public NumericKeypadFragment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      text = getArguments().getCharSequence(ARG_TEXT).toString();
      param = getArguments().getSerializable(ARG_PARAM);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    if (getDialog() != null) getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
    // Inflate the layout for this fragment
    View rootView = inflater.inflate(R.layout.fragment_numeric_keypad, container, false);


    for (int id: new int[] {R.id.Button0, R.id.Button1, R.id.Button2, R.id.Button3, R.id.Button4, R.id.Button5, R.id.Button6, R.id.Button7, R.id.Button8, R.id.Button9 }) {
      rootView.findViewById(id).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          onNumberClicked(v);
        }
      });
      rootView.findViewById(R.id.numpadBack).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          onBackClicked();
        }
      });
      rootView.findViewById(R.id.numpadDone).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          onDoneClicked();
        }
      });
      rootView.findViewById(R.id.numpadClear).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          onClearClicked();
        }
      });
      rootView.findViewById(R.id.dot).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          onDotClicked();
        }
      });
    }

    return rootView;
  }

  private void onDotClicked() {
    if (text.contains(".")) {
      return;
    }
    if (text.length()==0) {
      text="0";
    }
    text=text+".";
    listener().onNumericTextChanged(text, param);
  }

  private void onClearClicked() {
    if (text.length()!=0) {
      text="0";
      listener().onNumericTextChanged(text, param);
    }
  }

  private void onDoneClicked() {
    text=text.replaceFirst("\\.0*$", "");
    text=text.replaceFirst("(\\.[0-9]*[1-9])0*$", "$1");
    listener().onNumericTextChanged(text, param);
    listener().onNumericDone(param);
    dismiss();
  }

  private void onBackClicked() {
    if (text.length()>0) {
      text=text.substring(0,text.length()-1);
    }
    if (text.length()==0) {
      text="0";
    }
    listener().onNumericTextChanged(text, param);
  }

  public void onNumberClicked(View view) {
    String ch=((Button)view).getText().toString();
    if ("0".equals(text)) {
      text="";
    }
    text=text+((Button)view).getText();
    listener().onNumericTextChanged(text, param);
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    if (activity instanceof Listener) {
      mListener = (Listener) activity;
    } else {
      mListener=null;
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  @Override
  public void onCancel(DialogInterface dialog) {
    super.onCancel(dialog);
    listener().onNumericCanceled(param);
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
    void onNumericTextChanged(String text, Serializable param);
    void onNumericDone(Serializable param);
    void onNumericCanceled(Serializable param);
  }
}
