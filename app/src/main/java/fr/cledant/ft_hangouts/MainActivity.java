package fr.cledant.ft_hangouts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity
		implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//Bind Toolbar
		Toolbar toolbar = findViewById(R.id.toolbar_main);
		setSupportActionBar(toolbar);

		//Bind Action Bar + Drawer toogle button
		DrawerLayout drawer = findViewById(R.id.drawer_layout_main);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
		drawer.addDrawerListener(toggle);
		toggle.syncState();

		//Bind Navigation Bar
		NavigationView navigationView = findViewById(R.id.nav_view_main);
		navigationView.setNavigationItemSelectedListener(this);

		//Contact Grid
		GridView gridView = findViewById(R.id.contact_grid);
		List<Contact> image_details = getContactData();
		gridView.setAdapter(new ContactGridAdapter(this, image_details));
		gridView.setOnItemClickListener(this);
	}

	//Test data for display
	private List<Contact> getContactData()
	{
		List<Contact> list = new ArrayList<Contact>();
		Contact toto = new Contact(1, "To", "To", "Toto", "", "");
		Contact titi = new Contact(1, "Ti", "Ti", "Titi", "", "");
		Contact tata = new Contact(1, "Ta", "Ta", "Tata", "", "");
		Contact foo = new Contact(1, "Foo", "Bar", "", "", "");
		Contact bar = new Contact(1, "Bar", "Foo", "", "", "");

		list.add(toto);
		list.add(titi);
		list.add(tata);
		list.add(foo);
		list.add(bar);

		return list;
	}

	//Click on GridView Item behaviour
	@Override
	public void onItemClick(AdapterView<?> a, View v, int position, long id)
	{
		GridView gridView = findViewById(R.id.contact_grid);
		Contact contact = (Contact) gridView.getItemAtPosition(position);

		Intent intent = new Intent(this, DisplayContactActivity.class);
		intent.putExtra("ID", contact.getId());
		startActivity(intent);
	}

	//Back button behaviour
	@Override
	public void onBackPressed()
	{
		DrawerLayout drawer = findViewById(R.id.drawer_layout_main);
		if (drawer.isDrawerOpen(GravityCompat.START))
		{
			drawer.closeDrawer(GravityCompat.START);
		}
		else
		{
			super.onBackPressed();
		}
	}

	// Inflate the option menu; this adds items to the action bar if it is present.
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	//Action Bar
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	//Navigation Bar
	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.nav_new_contact:
			{
				Intent intent = new Intent(this, AddUserActivity.class);
				startActivity(intent);
				break;
			}
			case R.id.nav_settings:
			{
				Intent intent = new Intent(this, SettingsActivity.class);
				startActivity(intent);
				break;
			}
			default:
				break;
		}
		DrawerLayout drawer = findViewById(R.id.drawer_layout_main);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}
}
