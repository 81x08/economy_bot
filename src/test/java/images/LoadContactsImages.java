package images;

import org.junit.Test;

import ru.ras.economybot.telegram.images.ContactsImages;

public class LoadContactsImages {

    @Test
    public void begin() {
        System.out.println(ContactsImages.getImages());
    }

}