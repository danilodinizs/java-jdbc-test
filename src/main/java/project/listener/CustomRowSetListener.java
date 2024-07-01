package project.listener;

import lombok.extern.log4j.Log4j2;

import javax.sql.RowSet;
import javax.sql.RowSetEvent;
import javax.sql.RowSetListener;
import java.sql.SQLException;



@Log4j2
public class CustomRowSetListener implements RowSetListener {
    @Override
    public void rowSetChanged(RowSetEvent event) {
        System.out.println("Command execute triggered");
    }

    @Override
    public void rowChanged(RowSetEvent event) {
        System.out.println("Row inserted, updated or deleted");
        if(event.getSource() instanceof RowSet) {
            try {
                ((RowSet) event.getSource()).execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void cursorMoved(RowSetEvent event) {
        System.out.println("Cursor moved");
    }
}
