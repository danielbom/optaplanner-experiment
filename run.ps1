function BuildAndRun {
    param(
        [string]$data,
        [string]$time,
        [string]$constraints
    )
    python scripts\cli.py build-template -d $data -t $time -c $constraints -o ".\src\main\java\com\ipb\Constants.java"
    Write-Output "Constants.java updated with $data, $constraints, $time"
    
    Write-Output "Building the project"
    mvn clean compile -DskipTests -q

    Write-Output "Running the project"
    mvn exec:java -q
}

# Permute
$Datas = "small", "large"
$Times = "5s", "20s", "1m"
$Constraints = "only_hard", "with_soft_1", "with_soft_2", "with_soft_3", "all"

foreach ($data in $Datas) {
    foreach ($time in $Times) {
        foreach ($constraint in $Constraints) {
            $Output = "reports/${data}_${constraint}_pt$time.log"
            if (Test-Path $Output) {
                Write-Output "Skipping $Output"
            } else {
                Write-Output "Running $Output"
                BuildAndRun -data $data -time $time -constraints $constraint
            }
            Write-Output "----------------------------------------"
        }
    }
}
