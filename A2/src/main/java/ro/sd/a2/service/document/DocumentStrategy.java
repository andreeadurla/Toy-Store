package ro.sd.a2.service.document;

import java.io.File;
import java.io.IOException;

public interface DocumentStrategy<T> {

    File generate(T t) throws IOException;
}
