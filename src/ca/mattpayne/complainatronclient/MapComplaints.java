package ca.mattpayne.complainatronclient;

//TODO - Figure out how to get the location stuff emulating
import java.util.List;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ZoomControls;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

import ca.mattpayne.complainatronclient.models.Complaint;

public class MapComplaints extends AbstractMapActivity
{
	private int START_ZOOM = 1;
	private MapView mapView;
	private MapController controller;
	private List<Complaint> complaints;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_complaints);
        hookupControls();
        populateMap();
    }

	private void hookupControls()
    {
    	mapView = (MapView)findViewById(R.id.mapView);
        controller = mapView.getController();
    }

	private void populateMap()
	{
		complaints = dataAccessor.fetchComplaints(0);
		controller.setZoom(START_ZOOM);
		mapView.setStreetView(false);
		ZoomControls zoomControls = (ZoomControls)mapView.getZoomControls();
		zoomControls.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		mapView.addView(zoomControls);
		mapView.displayZoomControls(true);
		Resources r = getResources();
		Drawable marker = r.getDrawable(android.R.drawable.btn_star);
		marker.setBounds(0, 0, marker.getIntrinsicWidth(), marker.getIntrinsicHeight());
		Overlay overlay = new ItemizedComplaintOverlay(marker, complaints, this, dataAccessor);
		List<Overlay> overlays = mapView.getOverlays();
		overlays.add(overlay);
		mapView.postInvalidate();
	}
}
