package feng.reskin.demo;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import skin.lib.BaseSkinFragment;
import skin.lib.DynamicViewAttribute;


/**
 * 演示Fragment
 */
public class ReskinSystemViewFragment extends BaseSkinFragment {
    ViewStub viewStub;
    boolean isInflated = false;

    public ReskinSystemViewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reskin_system_view, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.listView);
        listView.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.listview_item, new
                String[]{"list " +
                "item 1", "list item 2", "list item 3"}));

        /** ViewStub */
        viewStub = (ViewStub) rootView.findViewById(R.id.stub);
        rootView.findViewById(R.id.inflateViewStub).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isInflated) {
                    viewStub.inflate();
                    isInflated = true;
                }
            }
        });

        /** 动态添加系统View */
        LinearLayout linearLayout = (LinearLayout) rootView.findViewById(R.id.linearLayout);
        TextView textView = new TextView(getActivity());
        textView.setText("手动add TextView textColor @color");
        int colorResId = R.color.textColor;
        textView.setTextColor(getResources().getColor(colorResId));
        linearLayout.addView(textView);

        /** 动态添加系统View不走onCreateView,需要手动添加换肤关注属性 */
        List<DynamicViewAttribute> attrs = new ArrayList<>();
        attrs.add(new DynamicViewAttribute("textColor", colorResId));
        addSkinView(textView, attrs);

        return rootView;
    }

}
