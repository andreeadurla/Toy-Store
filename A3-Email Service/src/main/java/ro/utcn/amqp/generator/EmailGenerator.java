package ro.utcn.amqp.generator;

import ro.utcn.amqp.entity.Email;

public interface EmailGenerator<T> {

    Email generate(T t);
}
