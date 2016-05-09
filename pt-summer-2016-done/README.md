Prerequisites:
1. /src/main/resources/application.properties - change path of the directory where the data would be saved/retrieved
2. /src/test/resources/application.properties - change the path to application test resources folder

- Make sure the URIs from application.properties end in '/'
- Make sure the application.properties file has proper encoding as otherwise the application will fail
- Tested successfully in IntellJ and Debian

Urls:
1. push?message=local.random.diceroll%209%201462702216167
2. aggr/query?pattern=local.*&start=1461162542693%20&end=1461162682693

Things to take into consideration:
1. The application computes in memory all the aggregations by second and by minute respectively.
2. The application serializes internal state into separate files as it was needed especially for average
aggregations to know the total number of metric into a specific avg aggregation.
3. Therefore, when a new request come the processor take previous aggregations from it's internal serialized file,
processes the new information along with the old one. After which the aggregation is saved into metric's 1SecondAvg,
1SecondMax, 1MinuteAvg, 1MinuteMax respectively. The last step is for processor to save it's internal state again

Sample requests:
    http://localhost:8080/push?message=local.random.diceroll 6 1462739588945
    http://localhost:8080/push?message=local.random.diceroll 4 1462739588945 - same second/minute as above
    http://localhost:8080/push?message=local.random.diceroll 7 1462739587945
    http://localhost:8080/push?message=local.random.diceroll 9 1462739587945 - same second/minute as above / overall same minute

    http://localhost:8080/aggr/query?pattern=local.*.*&start=1462739586000%20&end=1462739587000
        [{"name":"local.random.diceroll.1SecongAvg",
        "datapoints":[{"value":8.0,"timestamp":"1462739587000"}]},

        {"name":"local.random.diceroll.1SecondMax",
        "datapoints":[{"value":9.0,"timestamp":"1462739587000"}]},

        {"name":"local.random.diceroll.1MinuteAvg",
        "datapoints":[{"value":6.5,"timestamp":"1462739587000"}]},

        {"name":"local.random.diceroll.1MinuteMax",
        "datapoints":[{"value":9.0,"timestamp":"1462739587000"}]}]