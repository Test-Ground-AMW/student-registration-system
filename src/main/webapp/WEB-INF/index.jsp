<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student Registration</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@100;300;400;500;700;900&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
    <link rel="stylesheet" href="style.css">
</head>
<body>
<header class="p-2 text-center border-bottom">
<h1 class="mb-0 text-primary-emphasis">
    <i class="bi bi-person-plus"></i>
    Student Explorer</h1>
</header>
<main class="container-fluid">
    <div class="row">
        <div class="col-sm-4">
            <h4 mt-2> Student Details</h4>
            <form action="#" method="POST" enctype="multipart/form-data">
                <div class="mb-3">
                    <label for="txt-name" class="form-label">Student Name<b class="text-danger">*</b></label>
                    <input name="name" required pattern="^[A-Za-z ]+$" type="text" class="form-control" id="txt-name" placeholder="Ex: Achinda Mihiruk">
                </div>
                <div class="mb-3">
                    <label for="txt-address" class="form-label">Student Address<b class="text-danger">*</b></label>
                    <input required minlength="2" name="address" type="text" class="form-control" id="txt-address" placeholder="Ex: Gampaha">
                </div>
                <div class="mb-3">
                    <label for="img-student" class="form-label">Student Picture</label>
                    <input name="picture" class="form-control" type="file" id="img-student" accept="image/*">
                </div>
                <div class="mb-3">
                    <button class="btn btn-primary">Save</button>
                    <button type="reset" class="btn btn-warning">Clear</button>
                </div>
            </form>
        </div>
        <div class="col-sm-8">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th class="text-center">Picture</th>
                    <th class="text-center">ID</th>
                    <th>Name</th>
                    <th>Address</th>
                    <th class="text-center"></th>
                </tr>
                </thead>
                <tbody class="table-group-divider">
                <tr>
                    <td class="text-center"><img class="profile-picture" src="img/avatar.png" alt="profile pic"></td>
                    <td class="text-center">1</td>
                    <td>Otto</td>
                    <td>Gampaha</td>
                    <td class="text-center"></td>
                </tr>
                <tr>
                    <td class="text-center"><img class="profile-picture" src="img/avatar.png" alt="profile pic"></td>
                    <td class="text-center">2</td>
                    <td>batto</td>
                    <td>Panadura</td>
                    <td class="text-center"></td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</main>
<footer></footer>
</body>
</html>