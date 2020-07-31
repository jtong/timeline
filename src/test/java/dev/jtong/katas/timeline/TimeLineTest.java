package dev.jtong.katas.timeline;


import dev.jtong.katas.timeline.model.impl.ArticleContent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TimeLineTest {

    @Nested
    @DisplayName("main logic")
    class MainLogic {
        @Test
        public void should_add_one_node_to_time_line() throws InterruptedException {
            TimeLine<ArticleContent> timeLine = new TimeLine();
            assertThat(timeLine.getHistories().size()).isEqualTo(0);

            TimeNode<ArticleContent> node1 = new TimeNode<>(
                    new ArticleContent("Hello", "HelloWorld"),
                    Instant.now());
            Thread.sleep(1);
            TimeNode<ArticleContent> node2 = new TimeNode<>(
                    new ArticleContent("Hi", "HiWorld"),
                    Instant.now());

            timeLine.addNewVersion(node1);
            timeLine.addNewVersion(node2);

            assertThat(timeLine.getHistories().size()).isEqualTo(2);
            assertThat(timeLine.getHistories().get(0).getData().getTitle()).isEqualTo("Hello");
            assertThat(timeLine.getHistories().get(0).getData().getContent()).isEqualTo("HelloWorld");
            assertThat(timeLine.getHistories().get(1).getData().getTitle()).isEqualTo("Hi");
            assertThat(timeLine.getHistories().get(1).getData().getContent()).isEqualTo("HiWorld");
        }
    }

    @Nested
    @DisplayName("details")
    class Detail {

        private TimeLine<ArticleContent> timeLine;
        private TimeNode<ArticleContent> node1;
        private TimeNode<ArticleContent> node2;
        private int originalNewestIndex;
        private int originalSize;


        @BeforeEach
        public void before() throws InterruptedException {
            timeLine = new TimeLine();
            assertThat(timeLine.getHistories().size()).isEqualTo(0);

            node1 = new TimeNode<>(
                    new ArticleContent("Hello", "HelloWorld"),
                    Instant.now());
            Thread.sleep(1);
            node2 = new TimeNode<>(
                    new ArticleContent("Hi", "HiWorld"),
                    Instant.now());

            timeLine.addNewVersion(node1);
            timeLine.addNewVersion(node2);
            originalNewestIndex = timeLine.getHistories().size() - 1;
            originalSize = timeLine.getHistories().size();
        }

        @Test
        public void should_get_newest_when_getNewest() {
            String actual = timeLine.getNewest().getData().getTitle();
            String expected = timeLine.getHistories().get(originalNewestIndex).getData().getTitle();
            assertThat(actual).isEqualTo(expected);
        }

        @Test
        public void should_restore_to_add_new_one_in_time_line() {
            timeLine.restore(0);
            String actual = timeLine.getNewest().getData().getTitle();
            String expected = timeLine.getHistories().get(0).getData().getTitle();
            assertThat(actual).isEqualTo(expected);
            assertThat(timeLine.getHistories().size()).isEqualTo(originalSize + 1);
        }

        @Test
        public void should_add_tag() {
            assertThat(timeLine.getTags().size()).isEqualTo(0);
            String tagName = "init";
            timeLine.tag(0, tagName);
            assertThat(timeLine.getTags().size()).isEqualTo(1);
            assertThat(timeLine.getTags().get(0)).isEqualTo(tagName);
        }

        @Test
        public void should_not_add_tag_with_same_name() {
            String tagName = "init";
            timeLine.tag(0, tagName);
            assertThatThrownBy(()->timeLine.tag(1, tagName)).isInstanceOf(TagSameNameException.class);
        }

        @Test
        public void should_return_true_when_hasTag_given_tag_is_existed_in_time_line() {
            String tagName = "init";
            assertThat(timeLine.hasTag(tagName)).isEqualTo(false);
            timeLine.tag(0, tagName);
            assertThat(timeLine.hasTag(tagName)).isEqualTo(true);
        }

        @Test
        public void should_get_by_tag() {
            String tagName = "init";
            timeLine.tag(0, tagName);

            timeLine.restore(tagName);
            assertThat(timeLine.getHistories().size()).isEqualTo(originalSize + 1);

            assertThat(timeLine.getNewest().getData().getTitle()).isEqualTo("Hello");
        }
    }
}
