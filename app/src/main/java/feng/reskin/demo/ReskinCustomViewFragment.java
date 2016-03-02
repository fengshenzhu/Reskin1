package feng.reskin.demo;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import skin.lib.BaseSkinFragment;


/**
 * 演示Fragment
 */
public class ReskinCustomViewFragment extends BaseSkinFragment {

    public ReskinCustomViewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reskin_custom_view, container, false);
        LinearLayout linearLayout = (LinearLayout) rootView.findViewById(R.id.linearLayout);

        /** 手动添加自行实现了onDraw()的自定义View */
        CustomView customView = new CustomView(getActivity());
        customView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams
                .MATCH_PARENT, 200));
        linearLayout.addView(customView);

        /** 手动添加未自行实现onDraw()的自定义View */
        CustomTextView customLinearLayout = new CustomTextView(getActivity());
        customLinearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams
                .MATCH_PARENT, 200));
        linearLayout.addView(customLinearLayout);

        return rootView;
    }

}
