package hu.engard.xpenses.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;

/**
 * Activities that contain this fragment must implement the
 * {@link Listener} interface
 * to handle interaction events.
 * Use the {@link TextEntryDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TextEntryDialogFragment extends DialogFragment {
  private static final String ARG_TITLE = "title";
  private static final String ARG_TEXT = "text";
  private static final String ARG_PARAM = "param";

  private static final String TAG= "TextEntryDialogFragment";

  // title to show
  private CharSequence title;
  // text to edit
  private CharSequence text;
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
    throw new RuntimeException("No Listener passed to "+TAG);
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param title Title to display
   * @param text Text to edit
   * @param param Opaque parameter passed to listener callbacks
   * @return A new instance of fragment.
   */
  public static TextEntryDialogFragment newInstance(CharSequence title, CharSequence text, Serializable param, android.app.Fragment listener) {
    TextEntryDialogFragment fragment = new TextEntryDialogFragment();
    Bundle args = new Bundle();
    args.putCharSequence(ARG_TITLE, title);
    args.putCharSequence(ARG_TEXT, text);
    args.putSerializable(ARG_PARAM, param);
    fragment.setArguments(args);
    fragment.setTargetFragment(listener, 0);
    return fragment;
  }

  public TextEntryDialogFragment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      title = getArguments().getCharSequence(ARG_TITLE);
      text = getArguments().getCharSequence(ARG_TEXT);
      param = getArguments().getSerializable(ARG_PARAM);
    }
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

    LinearLayout layout=new LinearLayout(this.getActivity());
    layout.setOrientation(LinearLayout.VERTICAL);

    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT);

    TextView tv = new TextView(this.getActivity());
    tv.setText(title);
    tv.setLayoutParams(lp);

    final EditText et=new EditText(this.getActivity());
    et.setText(text);
    et.setLayoutParams(lp);

    layout.addView(tv);
    layout.addView(et);

    builder
        .setView(layout)
        .setTitle(title)
        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int id) {
            listener().onTextEntered(et.getText().toString(), param);
          }
        })
        .setNegativeButton("Cancel",
            new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                listener().onTextEntryCanceled(param);
              }
            });
    return builder.create();
  }

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
    void onTextEntered(CharSequence text, Serializable param);
    void onTextEntryCanceled(Serializable param);
  }

}
