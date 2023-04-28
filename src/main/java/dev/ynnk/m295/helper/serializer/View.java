package dev.ynnk.m295.helper.serializer;

public class View {
    public static class Public{}
    public static class Metadata extends Public { }
    public static class Hide{};

    public static class AnswerQuestion extends Public{}

    public static class CreateGroup extends Public{}
    public static class UpdateGroup extends Public{}

    public static class CreateTest extends Public{}
    public static class UpdateTest extends Public{}

    public static class CreateAndUpdateUser extends Public{}

}
