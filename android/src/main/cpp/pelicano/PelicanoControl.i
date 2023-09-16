%module PelicanoControl
%include "std_string.i"
%include "ValidatorPelicano.hpp"
%include "StateMachine.hpp"
%include "PelicanoControl.hpp"
%{
#include "ValidatorPelicano.hpp"
#include "StateMachine.hpp"
#include "PelicanoControl.hpp"
using namespace PelicanoControl;
using namespace StateMachine;
using namespace ValidatorPelicano;
%}
