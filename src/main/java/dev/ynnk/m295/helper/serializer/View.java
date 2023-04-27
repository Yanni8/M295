package dev.ynnk.m295.helper.serializer;

public class View {
    public static class Public{}
    public static class Metadata extends Public { }

    public static class TestMetadata extends Metadata { }
    public static class AnswerMetadata extends Metadata { }
    public static class GroupMetadata extends Metadata { }
    public static class QuestionMetadata extends Metadata { }
    public static class SolutionMetadata extends Metadata { }
    public static class UserMetadata extends Metadata { }
    public static class AnswerPossibilitiesMetadata extends Metadata { }

}
