package serializers;

import serializers.avro.AvroGeneric;
import serializers.avro.AvroSpecific;
import serializers.cks.CksBinary;
import serializers.cks.CksText;
import serializers.jackson.*;
import serializers.javaxjson.*;
import serializers.json.*;
import serializers.msgpack.MsgPack;
import serializers.protobuf.Protobuf;
import serializers.protobuf.ProtobufJson;
import serializers.protostuff.Protostuff;
import serializers.protostuff.ProtostuffJson;
import serializers.xml.ExiExificient;
import serializers.xml.XmlJavolution;
import serializers.xml.XmlStax;
import serializers.xml.XmlXStream;

/**
 * Full test of various codecs, using a single <code>MediaItem</code>
 * as test data.
 */
public class BenchmarkRunner extends MediaItemBenchmark
{
    public static void main(String[] args) {
        new BenchmarkRunner().runBenchmark(args);
    }

    protected void addTests(TestGroups groups)
    {
        // Binary Formats; language-specific ones
        JavaBuiltIn.register(groups);
        JavaManual.register(groups);

// 06-May-2013, tatu: way too slow, commenting out for now, can add in slow section?
        Scala.register(groups); // ruediger: let them face the truth :-). Not a problem as now time based test duration
// hessian, kryo and wobly are Java object serializations
        Hessian.register(groups);
        Kryo.register(groups);
        FastSerialization.register(groups);
        Wobly.register(groups);
        JBossSerialization.register(groups);
        JBossMarshalling.register(groups);
// 06-May-2013, tatu: Fails on basic Java7, mismatch with Unsafe; commented out
//        Obser.register(groups);
        
        // Binary formats, generic: protobuf, thrift, avro, CKS, msgpack
        Protobuf.register(groups);
        // 16-May-2012, Nate: As discussed on mailing list, removed ActiveMQProtobuf as 
        // its lazy deserialization isn't comparable to other serializers.
        // ActiveMQProtobuf.register(groups);
        Protostuff.register(groups);
        Thrift.register(groups);
        AvroSpecific.register(groups);
        AvroGeneric.register(groups);
        CksBinary.register(groups);
        MsgPack.register(groups);

        // JSON
        JacksonJsonManual.register(groups);
        JacksonJsonDatabind.register(groups);
        JacksonJsonAfterburner.register(groups); // databind with bytecode generation (faster)
//        JacksonJsonTree.register(groups);
// 01-May-2012, tatu: not all that useful (IMO) for general comparisons
//        JacksonJsonDatabindWithStrings.register(groups);
//        JacksonJsonTreeWithStrings.register(groups);
        JavaxJsonTreeGlassfish.register(groups);
        JavaxJsonStreamGlassfish.register(groups);
        JsonTwoLattes.register(groups);
        ProtostuffJson.register(groups);

        ProtobufJson.register(groups);
        JsonGsonManual.register(groups);
        JsonGsonTree.register(groups);
        JsonGsonDatabind.register(groups);
        JsonSvensonDatabind.register(groups);
        FlexjsonDatabind.register(groups);
        JsonLibJsonDatabind.register(groups);
        FastJSONDatabind.register(groups);
        JsonSimpleWithContentHandler.register(groups);
//        JsonSimpleManualTree.register(groups);
        JsonSmartManualTree.register(groups);
        JsonDotOrgManualTree.register(groups);
        JsonijJpath.register(groups);
// JsonijManualTree.register(groups);
        JsonArgoTree.register(groups);
// 06-May-2013, tatu: Too slow (100x above fastest)
// JsonPathDeserializerOnly.register(groups);

        // Then JSON-like
        // CKS text is textual JSON-like format
        CksText.register(groups);
        // then binary variants
        // Smile is 1-to-1 binary JSON serialization
        JacksonSmileManual.register(groups);
        JacksonSmileDatabind.register(groups);
        JacksonSmileAfterburner.register(groups); // databind with bytecode generation (faster)

	// 06-May-2013, tatu: Unfortunately there is a version conflict
        //    here too -- commenting out, to let David fix it
//        ProtostuffSmile.register(groups);
        // BSON is JSON-like format with extended datatypes
        JacksonBsonDatabind.register(groups);
        MongoDB.register(groups);

        // YAML (using Jackson module built on SnakeYAML)
        JacksonYAMLDatabind.register(groups);

        // XML-based formats; first textual XML
        XmlStax.register(groups, true, true, false); // woodstox/aalto/-
        XmlXStream.register(groups);
        JacksonXmlDatabind.register(groups);
        XmlJavolution.register(groups);

        // Then binary XML; Fast Infoset, EXI
        XmlStax.register(groups, false, false, true); // -/-/fast-infoset
        ExiExificient.register(groups);

    }
}
