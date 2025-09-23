package be.mbolle.crochcounter.projects.model.exceptions

class NoActiveProjectException() : IllegalArgumentException(
    "Please select an active project."
)
